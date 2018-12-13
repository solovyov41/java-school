package com.tlg.core.service.impl;

import com.tlg.core.dao.CityDao;
import com.tlg.core.dao.exception.DaoException;
import com.tlg.core.dao.exception.error.DaoError;
import com.tlg.core.dto.CityDto;
import com.tlg.core.entity.City;
import com.tlg.core.service.CityService;
import com.tlg.core.service.exceptions.ServiceException;
import com.tlg.core.service.exceptions.error.ServiceError;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CityServiceImpl implements CityService {

    @Autowired
    CityDao cityDao;

    @Autowired
    ModelMapper modelMapper;

    @Transactional
    @Override
    public CityDto findOrCreate(CityDto cityDto) throws ServiceException {

        try {
            City city = cityDao.findOrCreateCity(modelMapper.map(cityDto, City.class));
            return modelMapper.map(city, CityDto.class);
        } catch (DaoException ex) {
            if (ex.getError() == DaoError.QUERY_ERROR) {
                throw new ServiceException(ex, ServiceError.CANT_CREATE, cityDto);
            }
            throw new ServiceException(ex, ServiceError.SEARCH_ERROR, cityDto);
        }
    }

    @Override
    public CityDto create(CityDto newObject) throws ServiceException {
        return null;
    }

    @Override
    public CityDto findByIdentifier(String identifier) throws ServiceException {
        return null;
    }

    @Override
    public List<CityDto> findAll() throws ServiceException {
        return null;
    }

    @Override
    public CityDto update(String identifier, CityDto updatedObject) throws ServiceException {
        return null;
    }

    @Override
    public boolean delete(String identifier) throws ServiceException {
        return false;
    }
}
