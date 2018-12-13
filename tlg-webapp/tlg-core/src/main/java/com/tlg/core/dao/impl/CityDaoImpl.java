package com.tlg.core.dao.impl;

import com.tlg.core.dao.CityDao;
import com.tlg.core.dao.exception.DaoException;
import com.tlg.core.dao.exception.error.DaoError;
import com.tlg.core.entity.City;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

@Repository("cityDao")
public class CityDaoImpl extends AbstractDao<City, Long> implements CityDao {

    private static final Logger logger = LoggerFactory.getLogger(CityDaoImpl.class);

    public CityDaoImpl() {
        super(City.class);
    }

    @Override
    public City findByName(String name) throws DaoException {
        try {
            TypedQuery<City> query = entityManager.createNamedQuery("City.findByName", City.class);
            query.setParameter("name", name);
            City city = query.getSingleResult();

            logger.info("Found: {}.", city);
            return city;
        } catch (NoResultException ex) {
            logger.info("No city with name {} was found.", name);
            return null;
        } catch (PersistenceException ex) {
            throw new DaoException(ex, DaoError.SEARCH_QUERY_ERROR, City.class.getSimpleName(), name);
        }
    }

    @Override
    public City findByCoordinates(Double longitude, Double latitude) throws DaoException {
        try {
            TypedQuery<City> query = entityManager.createNamedQuery("City.findByCoordinates", City.class);
            query.setParameter("longitude", longitude);
            query.setParameter("latitude", latitude);
            City city = query.getSingleResult();

            logger.info("Found: {}.", city);
            return city;
        } catch (NoResultException ex) {
            logger.info("No city with longitude {} and latitude {} was found.", longitude, latitude);
            return null;
        } catch (PersistenceException ex) {
            throw new DaoException(ex, DaoError.SEARCH_QUERY_ERROR, City.class.getSimpleName(),
                    String.format("lat: %s and lng: %s", latitude, longitude));
        }
    }

    @Override
    public City findOrCreateCity(City city) throws DaoException {
        City foundCity = findByName(city.getName());

        if (foundCity != null) {
            city = foundCity;
        } else {
            create(city);
        }

        return city;
    }
}
