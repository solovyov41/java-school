package com.tlg.core.dao.impl;

import com.tlg.core.dao.VehicleDao;
import com.tlg.core.dao.exception.DaoException;
import com.tlg.core.dao.exception.error.DaoError;
import com.tlg.core.entity.Carriage;
import com.tlg.core.entity.City;
import com.tlg.core.entity.Vehicle;
import com.tlg.core.entity.enums.VehicleState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.util.List;

@Repository("vehicleDao")
public class VehicleDaoImpl extends AbstractDao<Vehicle, Long> implements VehicleDao {

    private static final Logger logger = LoggerFactory.getLogger(VehicleDaoImpl.class);

    public VehicleDaoImpl() {
        super(Vehicle.class);
    }

    @Override
    public Vehicle findByLicPlateNum(String licPlateNum) throws DaoException {
        try {
            TypedQuery<Vehicle> query = entityManager.createNamedQuery("Vehicle.findByLicPlateNum", Vehicle.class);
            query.setParameter("licPlateNum", licPlateNum);
            Vehicle vehicle = query.getSingleResult();

            logger.info("Found: {}.", vehicle);
            return vehicle;
        } catch (NonUniqueResultException ex) {
            logger.warn("More than one vehicle with licence plate number {} was found.", licPlateNum);
            throw new DaoException(ex, DaoError.NOT_SINGLE_RESULT);
        } catch (NoResultException ex) {
            logger.warn("No vehicle with licence plate number {} was found.", licPlateNum);
            throw new DaoException(ex, DaoError.NO_RESULT);
        } catch (PersistenceException ex) {
            throw new DaoException(ex, DaoError.QUERY_ERROR);
        }
    }

    @Override
    public List<Vehicle> findByCity(City city) throws DaoException {
        try {
            TypedQuery<Vehicle> query = entityManager.createNamedQuery("Vehicle.findByCity", Vehicle.class);
            query.setParameter("city", city);
            List<Vehicle> vehicles = query.getResultList();

            logger.info("List of vehicles in city {}", city);
            for (Vehicle vehicle : vehicles) {
                logger.info("Vehicle list::{}", vehicle);
            }
            return vehicles;
        } catch (PersistenceException ex) {
            throw new DaoException(ex, DaoError.QUERY_ERROR);
        }
    }

    @Override
    public Vehicle findByOrder(Carriage carriage) throws DaoException {
        try {
            TypedQuery<Vehicle> query = entityManager.createNamedQuery("Vehicle.findByOrder", Vehicle.class);
            query.setParameter("carriage", carriage);
            Vehicle vehicle = query.getSingleResult();

            logger.info("Found: {}.", vehicle);
            return vehicle;
        } catch (NonUniqueResultException ex) {
            logger.warn("More than one vehicle for order {} was found.", carriage);
            throw new DaoException(ex, DaoError.NOT_SINGLE_RESULT);
        } catch (NoResultException ex) {
            logger.info("No vehicle int carriage  {} was found.", carriage);
            throw new DaoException(ex, DaoError.NO_RESULT);
        } catch (PersistenceException ex) {
            throw new DaoException(ex, DaoError.QUERY_ERROR);
        }
    }

    @Override
    public List<Vehicle> findAvailableForOrder(BigDecimal maxOrderWeight) throws DaoException {
        try {
            TypedQuery<Vehicle> query = entityManager.createNamedQuery("Vehicle.findAvailableForOrder", Vehicle.class);
            query.setParameter("state", VehicleState.OK);
            query.setParameter("maxOrderWeight", maxOrderWeight);
            List<Vehicle> vehicles = query.getResultList();

            logger.info("List of ready for order vehicles which can carry {} ton(s)", maxOrderWeight);
            for (Vehicle vehicle : vehicles) {
                logger.info("Vehicle list::{}", vehicle);
            }
            return vehicles;
        } catch (PersistenceException ex) {
            throw new DaoException(ex, DaoError.QUERY_ERROR);
        }
    }
}
