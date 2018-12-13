package com.tlg.core.dao.impl;

import com.tlg.core.dao.CargoDao;
import com.tlg.core.dao.exception.DaoException;
import com.tlg.core.dao.exception.error.DaoError;
import com.tlg.core.entity.Cargo;
import com.tlg.core.entity.Carriage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository("cargoDao")
public class CargoDaoImpl extends AbstractDao<Cargo, Long> implements CargoDao {

    private static final Logger logger = LoggerFactory.getLogger(CargoDaoImpl.class);

    public CargoDaoImpl() {
        super(Cargo.class);
    }

    @Override
    public List<Cargo> findByOrder(Carriage carriage) throws DaoException {
        try {
            TypedQuery<Cargo> query = entityManager.createNamedQuery("Cargo.findByOrder", Cargo.class);
            query.setParameter("carriage", carriage);
            List<Cargo> cargoes = query.getResultList();

            logger.info("List of cargoes for carriage{}", carriage);
            for (Cargo cargo : cargoes) {
                logger.info("Cargoes list::{}", cargo);
            }
            return cargoes;
        } catch (PersistenceException ex) {
            throw new DaoException(ex, DaoError.QUERY_ERROR);
        }
    }

    @Override
    public Cargo findByUniqueNumber(String uniqueNumber) throws DaoException {
        try {
            TypedQuery<Cargo> query = entityManager.createNamedQuery("Cargo.findByUniqueNumber", Cargo.class);
            query.setParameter("uniqueNumber", uniqueNumber);
            Cargo cargo = query.getSingleResult();

            logger.info("Found: {}.", cargo);
            return cargo;
        } catch (NoResultException ex) {
            logger.info("No cargoes with unique number {} was found.", uniqueNumber);
            return null;
        } catch (PersistenceException ex) {
            throw new DaoException(ex, DaoError.QUERY_ERROR);
        }
    }
}
