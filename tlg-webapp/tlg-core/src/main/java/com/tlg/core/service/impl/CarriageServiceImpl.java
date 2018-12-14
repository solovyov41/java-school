package com.tlg.core.service.impl;

import com.tlg.core.dao.CarriageDao;
import com.tlg.core.dao.CityDao;
import com.tlg.core.dao.DriverDao;
import com.tlg.core.dao.WaypointDao;
import com.tlg.core.dao.exception.DaoException;
import com.tlg.core.dao.exception.error.DaoError;
import com.tlg.core.dto.CarriageDto;
import com.tlg.core.dto.DriverDto;
import com.tlg.core.dto.WaypointDto;
import com.tlg.core.entity.*;
import com.tlg.core.entity.enums.CargoState;
import com.tlg.core.entity.enums.CarriageStatus;
import com.tlg.core.entity.enums.DriverStatus;
import com.tlg.core.service.CargoService;
import com.tlg.core.service.CarriageService;
import com.tlg.core.service.DriverService;
import com.tlg.core.service.VehicleService;
import com.tlg.core.service.exceptions.ServiceException;
import com.tlg.core.service.exceptions.error.ServiceError;
import com.tlg.core.utils.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service("carriageService")
public class CarriageServiceImpl implements CarriageService {

    private static final Logger logger = LoggerFactory.getLogger(CarriageServiceImpl.class);

    @Autowired
    private CarriageDao carriageDao;
    @Autowired
    private CityDao cityDao;
    @Autowired
    private DriverDao driverDao;
    @Autowired
    private WaypointDao waypointDao;

    @Autowired
    private VehicleService vehicleService;
    @Autowired
    private DriverService driverService;
    @Autowired
    private CargoService cargoService;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private MessageSender messageSender;

    @Autowired
    private UniqueIdGenerator uniqueIdGenerator;
    @Autowired
    private RouteCalcParameters routeCalcParameters;
    @Autowired
    private DistanceCalculator distanceCalculator;


    /**
     * Assign list of waypointDto to carriage
     *
     * @param waypointDtos List of waypointsDto for assigning to carriage
     * @param carriage     Carriage in which waypoints are going to be assigned
     * @return Sorted by position in route list of assigned waypoints
     * @throws ServiceException When could not find or create City for Waypoint
     */
    @Transactional(propagation = Propagation.MANDATORY)
    public List<Waypoint> assignWaypoints(List<WaypointDto> waypointDtos, final Carriage carriage) throws ServiceException {

        List<Waypoint> waypoints = modelMapper.map(waypointDtos, new TypeToken<List<Waypoint>>() {
        }.getType());

        HashMap<String, City> routeCities = new HashMap<>();
        City city;

        for (Waypoint waypoint : waypoints) {
            if (routeCities.containsKey(waypoint.getCity().getName())) {
                city = routeCities.get(waypoint.getCity().getName());
            } else {
                try {
                    city = cityDao.findOrCreateCity(waypoint.getCity());

                    routeCities.put(waypoint.getCity().getName(), city);
                } catch (DaoException ex) {
                    throw new ServiceException(ex, ServiceError.CANT_ASSIGN_CITY, waypoint.getCity().getName(),
                            waypoint.getClass().getSimpleName(),
                            String.format("carriage: %s, position: %s", waypoint.getCarriage(), waypoint.getPosition()));
                }
            }
            waypoint.setCity(city);
            waypoint.setCarriage(carriage);
        }

        if (carriage.getWaypoints() == null) {
            carriage.setWaypoints(waypoints);
        } else {
            for (Waypoint wp : carriage.getWaypoints()) {
                try {
                    waypointDao.delete(wp);
                } catch (DaoException ex) {
                    throw new ServiceException(ex, ServiceError.DELETE_ERROR, Carriage.class.getSimpleName(),
                            carriage.getUniqueNumber(),
                            String.format("waypoint with position %s and city %s", wp.getPosition(), wp.getCity().getName()));
                }
            }
            carriage.getWaypoints().clear();
            carriage.getWaypoints().addAll(waypoints);
        }
        carriage.getWaypoints().sort(Comparator.comparing(Waypoint::getPosition));
        return carriage.getWaypoints();
    }

    /**
     * Assign cargoes and corresponding waypoints from carriageDto to carriage
     *
     * @param carriageDto CarriageDto object
     * @param curCarriage Carriage object from database
     * @throws ServiceException When could not find or create city for waypoint
     */
    @Transactional(propagation = Propagation.MANDATORY)
    public void assignWaypointsAndCargoes(CarriageDto carriageDto, final Carriage curCarriage) throws ServiceException {
        assignWaypoints(carriageDto.getWaypoints(), curCarriage);

        double routeDistance = distanceCalculator.calculate(curCarriage.getWaypoints());
        curCarriage.setEstimatedLeadTime((int) Math.ceil(routeDistance / routeCalcParameters.getAverageSpeed()));

        cargoService.assignCargoes(carriageDto.getCargoes(), curCarriage);
    }

    @Override
    public CarriageDto getNewCarriage() {
        CarriageDto carriageDto = new CarriageDto();
        carriageDto.setUniqueNumber(uniqueIdGenerator.generateUniqueId(new Carriage()));
        carriageDto.setStatus(CarriageStatus.CREATED);
        carriageDto.setInitiateDate(new Date());
        carriageDto.setCargoes(new ArrayList<>());
        carriageDto.setWaypoints(new ArrayList<>());

        return carriageDto;
    }

    @Transactional
    @Override
    public CarriageDto create(CarriageDto carriageDto) throws ServiceException {
        try {

            Carriage carriage = modelMapper.map(carriageDto, Carriage.class, "CarriageDtoCarriage");
            carriage.setStatus(CarriageStatus.CREATED);
            carriage.setInitiateDate(new Date());

            assignWaypointsAndCargoes(carriageDto, carriage);

            carriageDao.create(carriage);

            modelMapper.map(carriage, carriageDto);
            messageSender.sendMessage(ChangesType.NEW_ORDER, carriageDto);

            return carriageDto;
        } catch (DaoException ex) {
            throw new ServiceException(ex, ServiceError.CANT_CREATE, carriageDto);
        }
    }

    @Transactional
    @Override
    public CarriageDto findByIdentifier(String uniqueNumber) throws ServiceException {
        try {
            Carriage carriage = carriageDao.findByUniqueNumber(uniqueNumber);
            CarriageDto carriageDto = new CarriageDto();
            modelMapper.map(carriage, carriageDto, "CarriageCarriageDto");
            carriageDto.getWaypoints().sort(Comparator.comparing(WaypointDto::getPosition));
            return carriageDto;
        } catch (DaoException ex) {
            if (ex.getError() == DaoError.NO_RESULT || ex.getError() == DaoError.NOT_SINGLE_RESULT) {
                logger.warn(ex.getMessage(), ex);
                return null;
            }
            throw new ServiceException(ex, ServiceError.SEARCH_ERROR, "Carriage with uniqueNumber: " + uniqueNumber);
        }
    }

    @Transactional
    @Override
    public List<CarriageDto> findAll() throws ServiceException {
        try {
            List<Carriage> carriages = carriageDao.findAll();
            return modelMapper.map(carriages, new TypeToken<List<CarriageDto>>() {
            }.getType());
        } catch (DaoException ex) {
            throw new ServiceException(ex, ServiceError.SEARCH_ERROR, "All orders");
        }
    }

    @Transactional
    @Override
    public CarriageDto update(String uniqueNumber, CarriageDto carriageDto) throws ServiceException {
        try {
            Carriage curCarriage = carriageDao.findByUniqueNumber(uniqueNumber);

            if (curCarriage.getStatus() != CarriageStatus.CREATED) {
                return null;
            }

            if (carriageDto.getDrivers() != null) {
                carriageDto.getDrivers().removeIf(d -> d.getPersonalNum() == null);
            }
            modelMapper.map(carriageDto, curCarriage, "CarriageDtoCarriage");

            if (carriageDto.getVehicle() != null) {
                vehicleService.assignVehicle(carriageDto.getVehicle().getLicPlateNum(), curCarriage);
            }

            if (carriageDto.getDrivers() != null && !carriageDto.getDrivers().isEmpty()) {
                List<String> personalNumbers = carriageDto.getDrivers().stream()
                        .map(DriverDto::getPersonalNum).collect(Collectors.toList());
                driverService.assignDrivers(personalNumbers, curCarriage);
            }

            if (carriageDto.getWaypoints() != null && carriageDto.getCargoes() != null) {
                assignWaypointsAndCargoes(carriageDto, curCarriage);
            }

            carriageDao.update(curCarriage);
            modelMapper.map(curCarriage, carriageDto, "CarriageCarriageDto");

            messageSender.sendMessage(ChangesType.UPDATE_ORDER, carriageDto);
            return carriageDto;
        } catch (DaoException ex) {
            if (ex.getError() == DaoError.NO_RESULT || ex.getError() == DaoError.NOT_SINGLE_RESULT) {
                logger.warn(ex.getMessage(), ex);
                return null;
            }
            throw new ServiceException(ex, ServiceError.UPDATE_ERROR, "uniqueNumber", uniqueNumber, carriageDto);
        }
    }

    @Transactional
    @Override
    public boolean delete(String uniqueNumber) throws ServiceException {
        try {
            Carriage carriage = carriageDao.findByUniqueNumber(uniqueNumber);
            if (carriage == null || carriage.getStatus() == CarriageStatus.IN_PROGRESS) {
                return false;
            }

            if (carriage.getVehicle() != null) {
                vehicleService.unassignVehicle(carriage);
            }
            if (carriage.getDrivers() != null) {
                driverService.unassignDrivers(carriage);
            }
            carriageDao.delete(carriage);
            return true;
        } catch (DaoException ex) {
            throw new ServiceException(ex, ServiceError.DELETE_ERROR, "uniqueNumber", uniqueNumber, "Carriage");
        }
    }

    @Transactional
    @Override
    public List<CarriageDto> findNotAssigned() throws ServiceException {
        try {
            List<Carriage> carriages = carriageDao.findWithoutAssignment();
            return modelMapper.map(carriages, new TypeToken<List<CarriageDto>>() {
            }.getType());
        } catch (DaoException ex) {
            throw new ServiceException(ex, ServiceError.SEARCH_ERROR, "Carriage without assignments.");
        }
    }

    @Transactional
    @Override
    public List<CarriageDto> findInProcess() throws ServiceException {
        try {
            List<Carriage> carriages = carriageDao.findInProcess();
            return modelMapper.map(carriages, new TypeToken<List<CarriageDto>>() {
            }.getType());
        } catch (DaoException ex) {
            throw new ServiceException(ex, ServiceError.SEARCH_ERROR, "Carriage without assignments.");
        }
    }

    @Transactional
    @Override
    public List<CarriageDto> getLastCarriages(Integer number) {
        try {
            List<Carriage> carriages = carriageDao.findLast(number);
            List<CarriageDto> carriageDtos = new ArrayList<>(carriages.size());
            for (Carriage carriage : carriages) {
                carriageDtos.add(modelMapper.map(carriage, CarriageDto.class, "CarriageCarriageDto"));
            }
            return carriageDtos;
        } catch (DaoException ex) {
            logger.warn(ex.getMessage(), ex);
            return Collections.emptyList();
        }
    }

    @Transactional
    @Override
    public boolean checkInArrival(String carriageNumber, Integer waypointPosition, User user) throws ServiceException {
        try {
            Driver curDriver = driverDao.findByUser(user);
            Carriage carriage = carriageDao.findByUniqueNumber(carriageNumber);
            if (!carriage.getDrivers().contains(curDriver)) {
                return false;
            }

            carriage.getWaypoints().sort(Comparator.comparing(Waypoint::getPosition));
            Waypoint curWaypoint = carriage.getWaypoints().get(waypointPosition);
            if (curWaypoint.getVisited()) {
                return false;
            }
            curWaypoint.setVisited(true);
            boolean isLast = carriage.getWaypoints().size() - 1 == waypointPosition;

            carriage.getVehicle().setCity(curWaypoint.getCity());

            for (Driver driver : carriage.getDrivers()) {
                driver.setCity(curWaypoint.getCity());
                if (isLast) {
                    driverService.changeDriverStatus(driver, DriverStatus.REST);
                } else {
                    if (driver.getStatus() == DriverStatus.DRIVE) {
                        driverService.changeDriverStatus(driver, DriverStatus.IN_SHIFT);
                    }
                }
            }

            for (Cargo cargo : carriage.getCargoes()) {
                if (cargo.getDepartureWaypoint().equals(curWaypoint)) {
                    cargo.setState(CargoState.LOADED);
                }
                if (cargo.getDestinationWaypoint().equals(curWaypoint)) {
                    cargo.setState(CargoState.DELIVERED);
                }
            }

            if (isLast) {
                carriage.setStatus(CarriageStatus.DONE);
                carriage.setFinishDate(new Date());
                driverService.unassignDrivers(carriage);
                vehicleService.unassignVehicle(carriage);
            }
            carriageDao.update(carriage);
            messageSender.sendMessage(ChangesType.UPDATE_ORDER, modelMapper.map(carriage, CarriageDto.class));
            return true;
        } catch (DaoException ex) {
            throw new ServiceException(ex, ServiceError.CHECKIN_ERROR, carriageNumber, waypointPosition, user.getEmail());
        }
    }
}
