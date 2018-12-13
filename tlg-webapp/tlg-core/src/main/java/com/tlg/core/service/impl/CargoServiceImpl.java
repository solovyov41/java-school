package com.tlg.core.service.impl;

import com.tlg.core.dao.CargoDao;
import com.tlg.core.dao.CarriageDao;
import com.tlg.core.dao.exception.DaoException;
import com.tlg.core.dao.exception.error.DaoError;
import com.tlg.core.dto.CargoDto;
import com.tlg.core.dto.CarriageDto;
import com.tlg.core.entity.Cargo;
import com.tlg.core.entity.Carriage;
import com.tlg.core.entity.Waypoint;
import com.tlg.core.entity.enums.CargoState;
import com.tlg.core.service.CargoService;
import com.tlg.core.service.exceptions.ServiceException;
import com.tlg.core.service.exceptions.error.ServiceError;
import com.tlg.core.utils.ChangesType;
import com.tlg.core.utils.MessageSender;
import com.tlg.core.utils.UniqueIdGenerator;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CargoServiceImpl implements CargoService {

    private static final Logger logger = LoggerFactory.getLogger(CargoServiceImpl.class);

    @Autowired
    private CargoDao cargoDao;
    @Autowired
    private CarriageDao carriageDao;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private MessageSender messageSender;


    @Autowired
    private UniqueIdGenerator uniqueIdGenerator;

    @Transactional
    @Override
    public CargoDto create(CargoDto cargoDto) throws ServiceException {
        try {
            Cargo cargo = modelMapper.map(cargoDto, Cargo.class);
            cargo.setCarriage(carriageDao.findByUniqueNumber(cargoDto.getCarriage().getUniqueNumber()));

            cargoDao.create(cargo);

            modelMapper.map(cargo, cargoDto);
            messageSender.sendMessage(ChangesType.UPDATE_ORDER, cargoDto.getCarriage());
            return cargoDto;
        } catch (DaoException ex) {
            if (ex.getError() == DaoError.NO_RESULT || ex.getError() == DaoError.NOT_SINGLE_RESULT) {
                logger.warn(String.format("For new cargo with unique number %s searching carriage with unique number %s",
                        cargoDto.getNumber(), cargoDto.getCarriage().getUniqueNumber()), ex);
                return null;
            }
            throw new ServiceException(ex, ServiceError.CANT_CREATE, cargoDto);
        }
    }

    @Transactional
    @Override
    public CargoDto findByIdentifier(String uniqueNumber) throws ServiceException {
        try {
            Cargo cargo = cargoDao.findByUniqueNumber(uniqueNumber);
            return modelMapper.map(cargo, CargoDto.class);
        } catch (DaoException ex) {
            if (ex.getError() == DaoError.NO_RESULT || ex.getError() == DaoError.NOT_SINGLE_RESULT) {
                logger.warn(String.format("Searching cargo with unique number %s", uniqueNumber), ex);
                return null;
            }
            throw new ServiceException(ex, ServiceError.SEARCH_ERROR, "Carriage with uniqueNumber: " + uniqueNumber);
        }
    }

    @Transactional
    @Override
    public List<CargoDto> findAll() throws ServiceException {
        try {
            List<Cargo> cargoes = cargoDao.findAll();
            return modelMapper.map(cargoes, new TypeToken<List<CargoDto>>() {
            }.getType());
        } catch (DaoException ex) {
            throw new ServiceException(ex, ServiceError.SEARCH_ERROR, "All cargoes");
        }
    }

    @Transactional
    @Override
    public CargoDto update(String uniqueNumber, CargoDto cargoDto) throws ServiceException {
        try {
            Cargo curCargo = cargoDao.findByUniqueNumber(uniqueNumber);

            modelMapper.map(cargoDto, curCargo, "CargoDtoCargo");

            List<Waypoint> wps = curCargo.getCarriage().getWaypoints();
            Waypoint wp = modelMapper.map(cargoDto.getDepartureWaypoint(), Waypoint.class);
            curCargo.setDepartureWaypoint(wps.get(wps.indexOf(wp)));
            wp = modelMapper.map(cargoDto.getDestinationWaypoint(), Waypoint.class);
            curCargo.setDestinationWaypoint(wps.get(wps.indexOf(wp)));

            cargoDao.update(curCargo);
            modelMapper.map(curCargo, cargoDto);
            messageSender.sendMessage(ChangesType.UPDATE_ORDER, cargoDto.getCarriage());
            return cargoDto;

        } catch (DaoException ex) {
            if (ex.getError() == DaoError.NO_RESULT || ex.getError() == DaoError.NOT_SINGLE_RESULT) {
                logger.warn(String.format("Searching cargo with unique number %s for update", uniqueNumber), ex);
                return null;
            }
            throw new ServiceException(ex, ServiceError.UPDATE_ERROR, "uniqueNumber", uniqueNumber, cargoDto);
        }
    }


    @Transactional
    @Override
    public boolean delete(String uniqueNumber) throws ServiceException {
        try {
            Cargo cargo = cargoDao.findByUniqueNumber(uniqueNumber);
            if (cargo == null) {
                return false;
            }

            Carriage carriage = cargo.getCarriage();
            cargoDao.delete(cargo);
            messageSender.sendMessage(ChangesType.UPDATE_ORDER, modelMapper.map(carriage, CarriageDto.class));
            return true;
        } catch (DaoException ex) {
            throw new ServiceException(ex, ServiceError.DELETE_ERROR, "uniqueNumber", uniqueNumber, "Cargo");
        }
    }

    @Override
    public CargoDto getNewCargo() {
        CargoDto cargoDto = new CargoDto();
        cargoDto.setNumber(uniqueIdGenerator.generateUniqueId(new Cargo()));
        return cargoDto;
    }

    /**
     * Assign list of cargoDto to carriage
     *
     * @param cargoDtos List of cargoDto for assigning to carriage
     * @param carriage  Carriage in which cargoes are going to be assigned
     * @return List of assigned cargoes
     */
    @Transactional(propagation = Propagation.MANDATORY)
    @Override
    public List<Cargo> assignCargoes(List<CargoDto> cargoDtos, final Carriage carriage) throws ServiceException {
        List<Cargo> cargoes = modelMapper.map(cargoDtos, new TypeToken<List<Cargo>>() {
        }.getType());

        if (carriage.getCargoes() == null) {
            carriage.setCargoes(cargoes);
        }

        carriage.getCargoes().removeIf(c -> !cargoes.contains(c));

        for (Cargo cargo : cargoes) {
            int index = carriage.getCargoes().indexOf(cargo);
            Cargo cargoForUpdate = cargo;
            if (index != -1) {
                cargoForUpdate = carriage.getCargoes().get(index);
                modelMapper.map(cargo, cargoForUpdate);
            }

            cargoForUpdate.setCarriage(carriage);
            cargoForUpdate.setState(CargoState.PREPARED);

            cargoForUpdate.setDepartureWaypoint(carriage.getWaypoints().get(cargo.getDepartureWaypoint().getPosition()));
            cargoForUpdate.setDestinationWaypoint(carriage.getWaypoints().get(cargo.getDestinationWaypoint().getPosition()));

            if (index == -1) {
                carriage.getCargoes().add(cargoForUpdate);
            }

        }

        return carriage.getCargoes();
    }

}
