package com.tlg.core.service;

import com.tlg.core.dao.exception.DaoException;
import com.tlg.core.dto.CarriageDto;
import com.tlg.core.dto.DriverDto;
import com.tlg.core.dto.DriverStatistics;
import com.tlg.core.dto.VehicleDto;
import com.tlg.core.entity.Carriage;
import com.tlg.core.entity.Driver;
import com.tlg.core.entity.User;
import com.tlg.core.entity.enums.DriverStatus;
import com.tlg.core.service.exceptions.ServiceException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface DriverService extends GenericService<DriverDto, String> {

    DriverDto findByUser(User user) throws ServiceException;

    List<DriverDto> findDriverForOrder(CarriageDto carriageDto, VehicleDto vehicleDto) throws ServiceException;

    DriverDto changeDriverStatus(User user, DriverStatus driverStatus) throws ServiceException;

    @Transactional
    DriverDto changeDriverStatus(Driver driver, DriverStatus driverStatus) throws ServiceException;

    DriverDto getNewDriver();

    @Transactional
    void assignDrivers(List<String> personalNumbers, Carriage curCarriage) throws DaoException;

    @Transactional
    void unassignDrivers(Carriage carriage);

    DriverStatistics getDriverStatistics();
}
