package com.tlg.core.dao;

import com.tlg.core.dao.exception.DaoException;
import com.tlg.core.entity.Carriage;
import com.tlg.core.entity.City;
import com.tlg.core.entity.Vehicle;

import java.util.List;

public interface CarriageDao extends GenericDao<Carriage, Long> {

    Carriage findByUniqueNumber(String uniqueNumber) throws DaoException;

    Carriage findByVehicle(Vehicle vehicle) throws DaoException;

    List<Carriage> findWithoutDriversByCity(City city) throws DaoException;

    List<Carriage> findWithoutAssignment() throws DaoException;

    List<Carriage> findInProcess() throws DaoException;

    List<Carriage> findByDepartureCity(City city) throws DaoException;

    List<Carriage> findLast(Integer number) throws DaoException;
}
