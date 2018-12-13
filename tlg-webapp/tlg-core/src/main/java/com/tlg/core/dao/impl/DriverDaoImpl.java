package com.tlg.core.dao.impl;

import com.tlg.core.dao.DriverDao;
import com.tlg.core.dao.exception.DaoException;
import com.tlg.core.dao.exception.error.DaoError;
import com.tlg.core.entity.Carriage;
import com.tlg.core.entity.City;
import com.tlg.core.entity.Driver;
import com.tlg.core.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository("driverDao")
public class DriverDaoImpl extends AbstractDao<Driver, Long> implements DriverDao {

    private static final String DRIVER_LIST = "Driver list::{}";

    private static final Logger logger = LoggerFactory.getLogger(DriverDaoImpl.class);

    public DriverDaoImpl() {
        super(Driver.class);
    }

    @Override
    public Driver findDriverByPersonalNumber(String driverPersonalNumber) throws DaoException {
        try {
            TypedQuery<Driver> query = entityManager.createNamedQuery("Driver.findByPersonalNum", Driver.class);
            query.setParameter("driverPersonalNumber", driverPersonalNumber);
            Driver driver = query.getSingleResult();

            logger.info("Found: {}", driver);
            return driver;
        } catch (NoResultException ex) {
            logger.warn("No drivers with personal number: {}", driverPersonalNumber);
            throw new DaoException(ex, DaoError.NO_RESULT);
        } catch (PersistenceException ex) {
            throw new DaoException(ex, DaoError.QUERY_ERROR);
        }
    }

    @Override
    public Driver findByUser(User user) throws DaoException {
        try {
            TypedQuery<Driver> query = entityManager.createNamedQuery("Driver.findByUser", Driver.class);
            query.setParameter("email", user.getEmail());
            Driver driver = query.getSingleResult();

            logger.info("Found: {}", driver);
            return driver;
        } catch (NoResultException ex) {
            logger.info("No drivers for user: {}", user);
            return null;
        } catch (PersistenceException ex) {
            throw new DaoException(ex, DaoError.QUERY_ERROR);
        }
    }

    @Override
    public List<Driver> findByCity(City city) throws DaoException {
        try {

            TypedQuery<Driver> query = entityManager.createNamedQuery("Driver.findByCity", Driver.class);
            query.setParameter("city", city.getName());
            List<Driver> drivers = query.getResultList();

            logger.info("Drivers list for {}", city);
            for (Driver driver : drivers) {
                logger.info(DRIVER_LIST, driver);
            }
            return drivers;
        } catch (PersistenceException ex) {
            throw new DaoException(ex, DaoError.QUERY_ERROR);
        }
    }

    @Override
    public List<Driver> findFreeDriversInCity(City city) throws DaoException {
        try {

            TypedQuery<Driver> query = entityManager.createNamedQuery("Driver.findFreeDriversInCity", Driver.class);
            query.setParameter("city", city.getName());
            List<Driver> drivers = query.getResultList();

            logger.info("Available for order drivers list in {}", city);
            for (Driver driver : drivers) {
                logger.info(DRIVER_LIST, driver);
            }
            return drivers;
        } catch (PersistenceException ex) {
            throw new DaoException(ex, DaoError.QUERY_ERROR);
        }
    }

    @Override
    public List<Driver> findByOrder(Carriage carriage) throws DaoException {
        try {

            TypedQuery<Driver> query = entityManager.createNamedQuery("Driver.findByOrder", Driver.class);
            query.setParameter("carriage", carriage.getUniqueNumber());
            List<Driver> drivers = query.getResultList();

            logger.info("Drivers list for {}", carriage);
            for (Driver driver : drivers) {
                logger.info(DRIVER_LIST, driver);
            }
            return drivers;
        } catch (PersistenceException ex) {
            throw new DaoException(ex, DaoError.QUERY_ERROR);
        }
    }
}