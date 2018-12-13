package com.tlg.core.dao;

import com.tlg.core.dao.exception.DaoException;
import com.tlg.core.entity.Carriage;
import com.tlg.core.entity.City;
import com.tlg.core.entity.Driver;
import com.tlg.core.entity.User;

import java.util.List;

public interface DriverDao extends GenericDao<Driver, Long> {

    Driver findDriverByPersonalNumber(String driverPersonalNumber) throws DaoException;

    Driver findByUser(User user) throws DaoException;

    List<Driver> findByCity(City city) throws DaoException;

    List<Driver> findFreeDriversInCity(City city) throws DaoException;

    List<Driver> findByOrder(Carriage carriage) throws DaoException;
}
