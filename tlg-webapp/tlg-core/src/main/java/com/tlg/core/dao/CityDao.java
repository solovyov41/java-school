package com.tlg.core.dao;

import com.tlg.core.dao.exception.DaoException;
import com.tlg.core.entity.City;

public interface CityDao extends GenericDao<City, Long> {

    public City findByName(String name) throws DaoException;

    public City findByCoordinates(Double longitude, Double latitude) throws DaoException;

    City findOrCreateCity(City city) throws DaoException;
}
