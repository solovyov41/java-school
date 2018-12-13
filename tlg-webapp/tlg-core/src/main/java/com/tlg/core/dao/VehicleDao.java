package com.tlg.core.dao;

import com.tlg.core.dao.exception.DaoException;
import com.tlg.core.entity.Carriage;
import com.tlg.core.entity.City;
import com.tlg.core.entity.Vehicle;

import java.math.BigDecimal;
import java.util.List;

public interface VehicleDao extends GenericDao<Vehicle, Long> {

    Vehicle findByLicPlateNum(String licPlateNum) throws DaoException;

    List<Vehicle> findByCity(City city) throws DaoException;

    Vehicle findByOrder(Carriage carriage) throws DaoException;

    List<Vehicle> findAvailableForOrder(BigDecimal maxOrderWeight) throws DaoException;
}
