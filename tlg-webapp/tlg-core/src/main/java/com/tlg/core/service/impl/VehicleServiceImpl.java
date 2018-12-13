package com.tlg.core.service.impl;

import com.tlg.core.dao.CityDao;
import com.tlg.core.dao.VehicleDao;
import com.tlg.core.dao.exception.DaoException;
import com.tlg.core.dao.exception.error.DaoError;
import com.tlg.core.dto.CarriageDto;
import com.tlg.core.dto.VehicleDto;
import com.tlg.core.dto.VehicleStatistics;
import com.tlg.core.entity.Carriage;
import com.tlg.core.entity.City;
import com.tlg.core.entity.Vehicle;
import com.tlg.core.entity.enums.VehicleState;
import com.tlg.core.service.VehicleService;
import com.tlg.core.service.exceptions.ServiceException;
import com.tlg.core.service.exceptions.error.ServiceError;
import com.tlg.core.utils.ChangesType;
import com.tlg.core.utils.DistanceCalculator;
import com.tlg.core.utils.MaxOrderWeightCalc;
import com.tlg.core.utils.MessageSender;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("vehicleService")
public class VehicleServiceImpl implements VehicleService {

    public static final String LIC_PLATE_NUM = "licPlateNum";
    private static final Logger logger = LoggerFactory.getLogger(VehicleServiceImpl.class);
    @Autowired
    private VehicleDao vehicleDao;
    @Autowired
    private CityDao cityDao;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private MessageSender messageSender;


    @Autowired
    private DistanceCalculator distanceCalculator;
    @Autowired
    private MaxOrderWeightCalc maxOrderWeightCalc;

    @Transactional
    @Override
    public VehicleDto create(VehicleDto vehicleDto) throws ServiceException {
        try {
            Vehicle vehicle = modelMapper.map(vehicleDto, Vehicle.class);

            vehicle.setCity(cityDao.findOrCreateCity(modelMapper.map(vehicleDto.getCity(), City.class)));
            vehicleDao.create(vehicle);

            messageSender.sendMessage(ChangesType.VEHICLE_STATISTICS,
                    new VehicleStatistics().addOne(vehicle.getState() == VehicleState.BROKEN));

            return modelMapper.map(vehicle, VehicleDto.class);
        } catch (DaoException ex) {
            throw new ServiceException(ex, ServiceError.CANT_CREATE, vehicleDto);
        }
    }

    @Transactional
    @Override
    public VehicleDto findByIdentifier(String licPlateNum) throws ServiceException {
        try {
            Vehicle vehicle = vehicleDao.findByLicPlateNum(licPlateNum);
            return modelMapper.map(vehicle, VehicleDto.class);
        } catch (DaoException ex) {
            if (ex.getError() == DaoError.NO_RESULT || ex.getError() == DaoError.NOT_SINGLE_RESULT) {
                return null;
            }
            throw new ServiceException(ex, ServiceError.SEARCH_ERROR, "Vehicle with licence plate number: " + licPlateNum);
        }
    }

    @Transactional
    @Override
    public List<VehicleDto> findAll() throws ServiceException {
        try {

            List<Vehicle> vehicles = vehicleDao.findAll();
            return modelMapper.map(vehicles, new TypeToken<List<VehicleDto>>() {
            }.getType());
        } catch (DaoException ex) {
            throw new ServiceException(ex, ServiceError.SEARCH_ERROR, "All vehicles");
        }
    }


    @Transactional
    @Override
    public VehicleDto update(String licPlateNum, VehicleDto vehicleDto) throws ServiceException {
        try {
            Vehicle curVehicle = vehicleDao.findByLicPlateNum(licPlateNum);
            if (curVehicle == null || curVehicle.getCarriage() != null) {
                return null;
            }
            modelMapper.map(vehicleDto, curVehicle);
            curVehicle.setCity(cityDao.findOrCreateCity(modelMapper.map(vehicleDto.getCity(), City.class)));

            vehicleDao.update(curVehicle);

            return modelMapper.map(curVehicle, VehicleDto.class);
        } catch (DaoException ex) {
            if (ex.getError() == DaoError.NO_RESULT || ex.getError() == DaoError.NOT_SINGLE_RESULT) {
                logger.warn(String.format("Searching vehicle with number %s for update", licPlateNum), ex);
                return null;
            }
            throw new ServiceException(ex, ServiceError.UPDATE_ERROR, LIC_PLATE_NUM, licPlateNum, vehicleDto);
        }
    }

    @Transactional
    @Override
    public boolean delete(String licPlateNum) throws ServiceException {
        try {
            Vehicle vehicle = vehicleDao.findByLicPlateNum(licPlateNum);
            if (vehicle == null || vehicle.getCarriage() != null) {
                return false;
            }

            boolean isBroken = vehicle.getState() == VehicleState.BROKEN;
            vehicleDao.delete(vehicle);

            messageSender.sendMessage(ChangesType.VEHICLE_STATISTICS, new VehicleStatistics().removeOne(isBroken));
            return true;
        } catch (DaoException ex) {
            throw new ServiceException(ex, ServiceError.DELETE_ERROR, LIC_PLATE_NUM, licPlateNum, "Vehicle");
        }
    }

    @Transactional
    @Override
    public List<VehicleDto> findAvailableForOrder(CarriageDto carriageDto) throws ServiceException {
        try {
            double carriageCityLat = carriageDto.getWaypoints().get(0).getCity().getLatitude();
            double carriageCityLng = carriageDto.getWaypoints().get(0).getCity().getLongitude();

            List<Vehicle> vehicles = vehicleDao.findAvailableForOrder(maxOrderWeightCalc.calculate(carriageDto));

            List<VehicleDto> vehicleDtos = modelMapper.map(vehicles, new TypeToken<List<VehicleDto>>() {
            }.getType());

            vehicleDtos.forEach(v -> v.setDistanceToCityOrderShipment((int) Math.ceil(distanceCalculator.calculate(
                    v.getCity().getLatitude(), v.getCity().getLongitude(), carriageCityLat, carriageCityLng))));

            return vehicleDtos;
        } catch (DaoException ex) {
            throw new ServiceException(ex, ServiceError.SEARCH_ERROR, "Available vehicle for carriage: " + carriageDto);
        }
    }

    @Transactional
    @Override
    public void assignVehicle(String licPlateNum, Carriage carriage) throws DaoException {
        VehicleStatistics vs = new VehicleStatistics();
        Vehicle vehicle = vehicleDao.findByLicPlateNum(licPlateNum);
        if (carriage.getVehicle() != null) {
            carriage.getVehicle().setCarriage(null);
            vs.freeOne();
        }
        carriage.setVehicle(vehicle);
        vehicle.setCarriage(carriage);
        vs.assignOne();
        messageSender.sendMessage(ChangesType.VEHICLE_STATISTICS, vs);
    }

    @Transactional
    @Override
    public void unassignVehicle(Carriage carriage) {
        VehicleStatistics vs = new VehicleStatistics();
        carriage.getVehicle().setCarriage(null);
        carriage.setVehicle(null);
        vs.freeOne();
        messageSender.sendMessage(ChangesType.VEHICLE_STATISTICS, vs);
    }

    @Transactional
    @Override
    public VehicleDto changeVehicleState(String licPlateNum, VehicleState vehicleState) throws ServiceException {
        try {
            Vehicle curVehicle = vehicleDao.findByLicPlateNum(licPlateNum);
            if (curVehicle.getState() == VehicleState.OK) {
                curVehicle.setState(VehicleState.BROKEN);
                messageSender.sendMessage(ChangesType.VEHICLE_STATISTICS, new VehicleStatistics().breakOne(curVehicle.getCarriage() != null));
            } else {
                curVehicle.setState(VehicleState.OK);
                messageSender.sendMessage(ChangesType.VEHICLE_STATISTICS, new VehicleStatistics().repairOne());
            }

            vehicleDao.update(curVehicle);

            return modelMapper.map(curVehicle, VehicleDto.class);
        } catch (DaoException ex) {
            throw new ServiceException(ex, ServiceError.UPDATE_ERROR, LIC_PLATE_NUM, licPlateNum, vehicleState);
        }
    }

    @Transactional
    @Override
    public VehicleStatistics getVehicleStatistics() {
        try {
            List<Vehicle> vehicles = vehicleDao.findAll();

            int free = 0;
            int inWork = 0;
            int broken = 0;
            for (Vehicle vehicle : vehicles) {
                if (vehicle.getState() == VehicleState.BROKEN) {
                    broken++;
                } else {
                    if (vehicle.getCarriage() == null) {
                        free++;
                    } else {
                        inWork++;
                    }
                }
            }
            if (vehicles.size() == free + inWork + broken) {
                return new VehicleStatistics(free, inWork, broken);
            } else {
                logger.warn("Vehicle statistics does not matches: total = free + work + broken.");
                return null;
            }
        } catch (DaoException ex) {
            logger.warn("Could not get all vehicles for calculating vehicle statistics", ex);
            return null;
        }
    }
}
