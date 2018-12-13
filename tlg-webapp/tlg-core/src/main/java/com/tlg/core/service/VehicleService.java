package com.tlg.core.service;

import com.tlg.core.dao.exception.DaoException;
import com.tlg.core.dto.CarriageDto;
import com.tlg.core.dto.VehicleDto;
import com.tlg.core.dto.VehicleStatistics;
import com.tlg.core.entity.Carriage;
import com.tlg.core.entity.enums.VehicleState;
import com.tlg.core.service.exceptions.ServiceException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface VehicleService extends GenericService<VehicleDto, String> {

    List<VehicleDto> findAvailableForOrder(CarriageDto carriageDto) throws ServiceException;

    @Transactional
    void assignVehicle(String licPlateNum, Carriage carriage) throws DaoException;

    VehicleDto changeVehicleState(String licPlateNum, VehicleState vehicleState) throws ServiceException;

    VehicleStatistics getVehicleStatistics();

    void unassignVehicle(Carriage carriage);
}
