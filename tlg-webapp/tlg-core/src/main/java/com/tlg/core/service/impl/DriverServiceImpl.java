package com.tlg.core.service.impl;

import com.tlg.core.dao.CityDao;
import com.tlg.core.dao.DriverDao;
import com.tlg.core.dao.UserDao;
import com.tlg.core.dao.exception.DaoException;
import com.tlg.core.dao.exception.error.DaoError;
import com.tlg.core.dto.CarriageDto;
import com.tlg.core.dto.DriverDto;
import com.tlg.core.dto.DriverStatistics;
import com.tlg.core.dto.VehicleDto;
import com.tlg.core.entity.Carriage;
import com.tlg.core.entity.City;
import com.tlg.core.entity.Driver;
import com.tlg.core.entity.User;
import com.tlg.core.entity.enums.CarriageStatus;
import com.tlg.core.entity.enums.DriverStatus;
import com.tlg.core.entity.enums.Role;
import com.tlg.core.service.DriverService;
import com.tlg.core.service.ShiftService;
import com.tlg.core.service.exceptions.ServiceException;
import com.tlg.core.service.exceptions.error.ServiceError;
import com.tlg.core.utils.ChangesType;
import com.tlg.core.utils.MessageSender;
import com.tlg.core.utils.RouteCalcParameters;
import com.tlg.core.utils.UniqueIdGenerator;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service("driverService")
public class DriverServiceImpl implements DriverService {

    private static final String DRIVER_STATUS_CHANGED = "Status for driver %s changed from %s to %s";

    private static final Logger logger = LoggerFactory.getLogger(DriverServiceImpl.class);

    @Autowired
    private DriverDao driverDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private CityDao cityDao;

    @Autowired
    private ShiftService shiftService;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private MessageSender messageSender;

    @Autowired
    private RouteCalcParameters routeCalcParameters;
    @Autowired
    private UniqueIdGenerator uniqueIdGenerator;

    @Transactional
    @Override
    public DriverDto create(DriverDto driverDto) throws ServiceException {
        try {
            Driver driver = modelMapper.map(driverDto, Driver.class);

            User user = userDao.findByEmail(driverDto.getUser().getEmail());
            user.setRole(Role.DRIVER);

            driver.setUser(user);
            driver.setCity(cityDao.findOrCreateCity(modelMapper.map(driverDto.getCity(), City.class)));
            driver.setStatus(DriverStatus.REST);

            driverDao.create(driver);
            messageSender.sendMessage(ChangesType.DRIVER_STATISTICS, new DriverStatistics().addOne());
            return modelMapper.map(driver, DriverDto.class);
        } catch (DaoException ex) {
            throw new ServiceException(ex, ServiceError.CANT_CREATE, driverDto);
        }
    }

    @Transactional
    @Override
    public DriverDto findByIdentifier(String personalNumber) throws ServiceException {
        try {
            Driver driver = driverDao.findDriverByPersonalNumber(personalNumber);
            return modelMapper.map(driver, DriverDto.class);
        } catch (DaoException ex) {
            if (ex.getError() == DaoError.NO_RESULT || ex.getError() == DaoError.NOT_SINGLE_RESULT) {
                return null;
            }
            throw new ServiceException(ex, ServiceError.SEARCH_ERROR, String.format("Driver with personal number %s", personalNumber));
        }
    }

    @Transactional
    @Override
    public DriverDto findByUser(User user) throws ServiceException {
        try {
            return modelMapper.map(driverDao.findByUser(user), DriverDto.class);
        } catch (DaoException ex) {
            throw new ServiceException(ex, ServiceError.SEARCH_ERROR, String.format("Driver for user %s", user));
        }
    }

    @Transactional
    @Override
    public List<DriverDto> findAll() throws ServiceException {
        try {
            List<Driver> drivers = driverDao.findAll();
            return modelMapper.map(drivers, new TypeToken<List<DriverDto>>() {
            }.getType());
        } catch (DaoException ex) {
            throw new ServiceException(ex, ServiceError.SEARCH_ERROR, "All drivers");
        }
    }

    @Transactional
    @Override
    public List<DriverDto> findDriverForOrder(CarriageDto carriageDto, VehicleDto vehicleDto) throws ServiceException {
        try {
            List<Driver> drivers = driverDao.findFreeDriversInCity(modelMapper.map(vehicleDto.getCity(), City.class));

            int carriageDeliveryDaysThisMonth = (int) Math.ceil(carriageDto.getEstimatedLeadTime() / (float) routeCalcParameters.getDayWorkTime());

            LocalDateTime estimatedFinishDate = LocalDateTime.now().plusDays((long) carriageDeliveryDaysThisMonth);
            if (estimatedFinishDate.getMonthValue() > LocalDateTime.now().getMonthValue()) {
                estimatedFinishDate = LocalDateTime.of(estimatedFinishDate.getYear(),
                        estimatedFinishDate.getMonthValue() + 1, 1, 0, 0);
                carriageDeliveryDaysThisMonth = (int) ChronoUnit.DAYS.between(LocalDate.now(), estimatedFinishDate);
            }

            List<DriverDto> driverDtos = new ArrayList<>(drivers.size());
            for (Driver driver : drivers) {
                if (carriageDeliveryDaysThisMonth * routeCalcParameters.getDayWorkTime() + shiftService.getWorkedTimeInCurrentMonth(driver)
                        <= routeCalcParameters.getMonthWorkTime()) {
                    driverDtos.add(modelMapper.map(driver, DriverDto.class));
                }
            }
            return driverDtos;
        } catch (DaoException ex) {
            throw new ServiceException(ex, ServiceError.SEARCH_ERROR, "All drivers");
        }
    }

    @Transactional
    @Override
    public DriverDto update(String personalNumber, DriverDto driverDto) throws ServiceException {
        try {
            Driver curDriver = driverDao.findDriverByPersonalNumber(personalNumber);
            if (curDriver == null || curDriver.getStatus() != DriverStatus.REST) {
                return null;
            }

            modelMapper.map(driverDto, curDriver);
            curDriver.setCity(cityDao.findOrCreateCity(modelMapper.map(driverDto.getCity(), City.class)));

            driverDao.update(curDriver);
            return modelMapper.map(curDriver, DriverDto.class);
        } catch (DaoException ex) {
            if (ex.getError() == DaoError.NO_RESULT || ex.getError() == DaoError.NOT_SINGLE_RESULT) {
                return null;
            }
            throw new ServiceException(ex, ServiceError.UPDATE_ERROR, "personalNumber", personalNumber, driverDto);
        }
    }

    @Transactional
    @Override
    public DriverDto changeDriverStatus(User user, DriverStatus driverStatus) throws ServiceException {
        try {
            Driver curDriver = driverDao.findByUser(user);
            return changeDriverStatus(curDriver, driverStatus);
        } catch (DaoException ex) {
            throw new ServiceException(ex, ServiceError.SEARCH_ERROR, String.format("driver by user %s", user.getEmail()));
        }
    }

    /**
     * Changes driver status
     * @param driver Driver  for changing status
     * @param driverStatus Driver status enum to be changed
     * @return DriverDto with changed status
     * @throws ServiceException When conditions for changing status are not satisfied.
     * e.g. changing to same status or trying to get in shift without assignment .
     */
    @Transactional
    @Override
    public DriverDto changeDriverStatus(Driver driver, DriverStatus driverStatus) throws ServiceException {
        try {
            if (driver.getCarriage() == null) {
                throw new ServiceException(ServiceError.DRIVER_STATUS_CHANGE, driver.getPersonalNum(),
                        "there is no assigned carriage for delivery");
            }

            switch (driver.getStatus()) {
                case REST:
                    switch (driverStatus) {
                        case REST:
                            throw new ServiceException(ServiceError.DRIVER_STATUS_CHANGE, driver.getPersonalNum(),
                                    "driver has already status REST");
                        case DRIVE:
                            throw new ServiceException(ServiceError.DRIVER_STATUS_CHANGE, driver.getPersonalNum(),
                                    "firstly need to enter status IN_SHIFT");
                        case IN_SHIFT:
                            logger.info(DRIVER_STATUS_CHANGED, driver.getStatus(), DriverStatus.IN_SHIFT);
                            driver.getCarriage().setStatus(CarriageStatus.IN_PROGRESS);
                            break;
                    }
                    break;
                case IN_SHIFT:
                    switch (driverStatus) {
                        case IN_SHIFT:
                            throw new ServiceException(ServiceError.DRIVER_STATUS_CHANGE, driver.getPersonalNum(),
                                    "driver has already status IN_SHIFT");
                        case DRIVE:
                            logger.info(DRIVER_STATUS_CHANGED, driver.getStatus(), DriverStatus.DRIVE);
                            for (Driver codriver : driver.getCarriage().getDrivers()) {
                                if (codriver.getStatus() == DriverStatus.DRIVE) {
                                    changeDriverStatus(codriver.getUser(), DriverStatus.IN_SHIFT);
                                }
                            }
                            shiftService.startShift(driver);
                            break;
                        case REST:
                            logger.info(DRIVER_STATUS_CHANGED, driver.getStatus(), DriverStatus.IN_SHIFT);
                            break;
                    }
                    break;
                case DRIVE:
                    switch (driverStatus) {
                        case DRIVE:
                            throw new ServiceException(ServiceError.DRIVER_STATUS_CHANGE, driver.getPersonalNum(),
                                    "driver has already status DRIVE");
                        case IN_SHIFT:
                            logger.info(DRIVER_STATUS_CHANGED, driver.getStatus(), DriverStatus.IN_SHIFT);
                            shiftService.endShift(driver);
                            break;
                        case REST:
                            logger.info(DRIVER_STATUS_CHANGED, driver.getStatus(), DriverStatus.REST);
                            shiftService.endShift(driver);
                            break;
                    }
                    break;
            }

            driver.setStatus(driverStatus);
            driverDao.update(driver);
        } catch (DaoException ex) {
            throw new ServiceException(ex, ServiceError.UPDATE_ERROR, "driverStatus", driverStatus, driver.getPersonalNum());
        }

        return modelMapper.map(driver, DriverDto.class);
    }

    @Transactional
    @Override
    public boolean delete(String personalNumber) throws ServiceException {
        try {
            Driver driver = driverDao.findDriverByPersonalNumber(personalNumber);
            if (driver == null || driver.getStatus() != DriverStatus.REST) {
                return false;
            }

            driverDao.delete(driver);
            messageSender.sendMessage(ChangesType.DRIVER_STATISTICS, new DriverStatistics().removeOne());
            return true;
        } catch (DaoException ex) {
            throw new ServiceException(ex, ServiceError.DELETE_ERROR, "personalNumber", personalNumber, "Driver");
        }
    }

    @Override
    public DriverDto getNewDriver() {
        DriverDto driverDto = new DriverDto();
        driverDto.setStatus(DriverStatus.REST);
        driverDto.setPersonalNum(uniqueIdGenerator.generateUniqueId(new Driver()));
        return driverDto;
    }

    @Transactional
    @Override
    public void assignDrivers(List<String> personalNumbers, Carriage carriage) throws DaoException {
        DriverStatistics ds = freeDrivers(carriage);
        for (String personalNumber : personalNumbers) {
            Driver driver = driverDao.findDriverByPersonalNumber(personalNumber);
            driver.setCarriage(carriage);
            driver.setVehicle(carriage.getVehicle());
            carriage.getDrivers().add(driver);
            ds.assignOne();
        }
        messageSender.sendMessage(ChangesType.DRIVER_STATISTICS, ds);
    }

    @Transactional
    @Override
    public void unassignDrivers(Carriage carriage) {
        DriverStatistics ds = freeDrivers(carriage);
        messageSender.sendMessage(ChangesType.DRIVER_STATISTICS, ds);
    }

    private DriverStatistics freeDrivers(Carriage carriage) {
        DriverStatistics ds = new DriverStatistics();
        carriage.getDrivers().forEach(driver -> {
            driver.setCarriage(null);
            driver.setVehicle(null);
            ds.freeOne();
        });
        carriage.getDrivers().clear();
        return ds;
    }

    @Transactional
    @Override
    public DriverStatistics getDriverStatistics() {
        try {
            List<Driver> drivers = driverDao.findAll();
            int free = 0;
            int inWork = 0;
            for (Driver driver : drivers) {
                switch (driver.getStatus()) {
                    case REST:
                        free++;
                        break;
                    case DRIVE:
                    case IN_SHIFT:
                        inWork++;
                        break;
                }
            }
            if (drivers.size() == free + inWork) {
                return new DriverStatistics(free, inWork);
            } else {
                logger.warn("Driver statistics does not matches: total = free + work.");
                return null;
            }
        } catch (DaoException ex) {
            logger.warn("Could not get all drivers for calculating driver statistics", ex);
            return null;
        }
    }
}
