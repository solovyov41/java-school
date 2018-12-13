package com.tlg.core.dao.impl;

import com.tlg.core.dao.CarriageDao;
import com.tlg.core.dao.exception.DaoException;
import com.tlg.core.dao.exception.error.DaoError;
import com.tlg.core.entity.Carriage;
import com.tlg.core.entity.City;
import com.tlg.core.entity.Vehicle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository("carriageDao")
public class CarriageDaoImpl extends AbstractDao<Carriage, Long> implements CarriageDao {

    private static final String ORDER_LIST = "Carriage list::{}";

    private static final Logger logger = LoggerFactory.getLogger(CarriageDaoImpl.class);

    public CarriageDaoImpl() {
        super(Carriage.class);
    }

    @Override
    public Carriage findByUniqueNumber(String uniqueNumber) throws DaoException {
        try {
            TypedQuery<Carriage> query = entityManager.createNamedQuery("Order.findByUniqueNumber", Carriage.class);
            query.setParameter("uniqueNumber", uniqueNumber);
            Carriage carriage = query.getSingleResult();

            logger.info("Found: {}.", carriage);
            return carriage;
        } catch (NoResultException ex) {
            logger.info("No orders with unique number {} was found.", uniqueNumber);
            return null;
        } catch (PersistenceException ex) {
            throw new DaoException(ex, DaoError.QUERY_ERROR);
        }
    }

    @Override
    public Carriage findByVehicle(Vehicle vehicle) throws DaoException {
        try {
            TypedQuery<Carriage> query = entityManager.createNamedQuery("Carriage.findByVehicle", Carriage.class);
            query.setParameter("vehicle", vehicle);
            Carriage carriage = query.getSingleResult();

            logger.info("Found: {}.", carriage);
            return carriage;
        } catch (NoResultException ex) {
            logger.info("No orders for vehicle {} was found.", vehicle);
            return null;
        } catch (PersistenceException ex) {
            throw new DaoException(ex, DaoError.QUERY_ERROR);
        }
    }

    @Override
    public List<Carriage> findWithoutDriversByCity(City city) throws DaoException {
        try {
            TypedQuery<Carriage> query = entityManager.createNamedQuery("Carriage.findWithoutDriversByCity", Carriage.class);
            query.setParameter("city", city);
            List<Carriage> carriages = query.getResultList();

            logger.info("List of carriages without driver in {}", city);
            for (Carriage carriage : carriages) {
                logger.info(ORDER_LIST, carriage);
            }
            return carriages;
        } catch (PersistenceException ex) {
            throw new DaoException(ex, DaoError.QUERY_ERROR);
        }
    }

    @Override
    public List<Carriage> findWithoutAssignment() throws DaoException {
        try {
            TypedQuery<Carriage> query = entityManager.createNamedQuery("Carriage.findWithoutAssignment", Carriage.class);
            List<Carriage> carriages = query.getResultList();

            logger.info("List of carriages without assignments");
            for (Carriage carriage : carriages) {
                logger.info(ORDER_LIST, carriage);
            }
            return carriages;
        } catch (PersistenceException ex) {
            throw new DaoException(ex, DaoError.QUERY_ERROR);
        }
    }

    @Override
    public List<Carriage> findInProcess() throws DaoException {
        try {
            TypedQuery<Carriage> query = entityManager.createNamedQuery("Carriage.findInProcess", Carriage.class);
            List<Carriage> carriages = query.getResultList();

            logger.info("List of carriages in work");
            for (Carriage carriage : carriages) {
                logger.info(ORDER_LIST, carriage);
            }
            return carriages;
        } catch (PersistenceException ex) {
            throw new DaoException(ex, DaoError.QUERY_ERROR);
        }
    }

    @Override
    public List<Carriage> findByDepartureCity(City city) throws DaoException {
        try {
            TypedQuery<Carriage> query = entityManager.createNamedQuery("Carriage.findByDepartureCity", Carriage.class);
            query.setParameter("city", city);
            List<Carriage> carriages = query.getResultList();

            logger.info("List of carriages with departure from {}", city);
            for (Carriage carriage : carriages) {
                logger.info(ORDER_LIST, carriage);
            }
            return carriages;
        } catch (PersistenceException ex) {
            throw new DaoException(ex, DaoError.QUERY_ERROR);
        }
    }

    @Override
    public List<Carriage> findLast(Integer number) throws DaoException {
        try {
            TypedQuery<Carriage> query = entityManager.createNamedQuery("Carriage.findLast", Carriage.class);
            query.setMaxResults(number);
            List<Carriage> carriages = query.getResultList();

            logger.info("List of {} last carriages", number);
            for (Carriage carriage : carriages) {
                logger.info(ORDER_LIST, carriage);
            }
            return carriages;
        } catch (PersistenceException ex) {
            throw new DaoException(ex, DaoError.QUERY_ERROR);
        }
    }

}
