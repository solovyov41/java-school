package com.tlg.core.service;

import com.tlg.core.dto.CityDto;
import com.tlg.core.service.exceptions.ServiceException;

public interface CityService extends GenericService<CityDto, String> {

    CityDto findOrCreate(CityDto cityDto) throws ServiceException;

}
