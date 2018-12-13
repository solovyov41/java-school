package com.tlg.core.service;

import com.tlg.core.dto.CarriageDto;
import com.tlg.core.entity.User;
import com.tlg.core.service.exceptions.ServiceException;

import java.util.List;

public interface CarriageService extends GenericService<CarriageDto, String> {

    List<CarriageDto> findNotAssigned() throws ServiceException;

    List<CarriageDto> findInProcess() throws ServiceException;

    CarriageDto getNewCarriage();

    List<CarriageDto> getLastCarriages(Integer number);

    boolean checkInArrival(String carriageNumber, Integer waypointPosition, User user) throws ServiceException;
}



