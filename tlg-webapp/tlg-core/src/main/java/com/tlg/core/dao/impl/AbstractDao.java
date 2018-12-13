package com.tlg.core.dao.impl;

import com.tlg.core.dao.GenericDao;
import com.tlg.core.dao.exception.DaoException;
import com.tlg.core.dao.exception.error.DaoError;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.io.Serializable;
import java.util.List;

public abstract class AbstractDao<T, K extends Serializable> implements GenericDao<T, K> {

    private static final String NULL_ARGUMENT = "Argument must not be null.";

    private final Class<T> clazz;
    @PersistenceContext
    EntityManager entityManager;

    public AbstractDao(Class<T> clazz) {
        this.clazz = clazz;
    }

    public T find(K id) throws DaoException {
        try {
            if (id == null) {
                throw new IllegalArgumentException(NULL_ARGUMENT);
            }
            return entityManager.find(clazz, id);
        } catch (PersistenceException ex) {
            throw new DaoException(ex, DaoError.QUERY_ERROR);
        }
    }

    @SuppressWarnings("unchecked")
    public List<T> findAll() throws DaoException {
        try {

            return entityManager.createQuery("FROM " + clazz.getSimpleName(), clazz)
                    .getResultList();
        } catch (PersistenceException ex) {
            throw new DaoException(ex, DaoError.QUERY_ERROR);
        }
    }


    public void create(T entity) throws DaoException {
        try {

            if (entity == null) {
                throw new IllegalArgumentException(NULL_ARGUMENT);
            }
            entityManager.persist(entity);
        } catch (PersistenceException ex) {
            throw new DaoException(ex, DaoError.QUERY_ERROR);
        }
    }

    public void update(T entity) throws DaoException {
        try {
            if (entity == null) {
                throw new IllegalArgumentException(NULL_ARGUMENT);
            }
            entityManager.merge(entity);
        } catch (PersistenceException ex) {
            throw new DaoException(ex, DaoError.QUERY_ERROR);
        }
    }

    public void delete(T entity) throws DaoException {
        try {
            if (entity == null) {
                throw new IllegalArgumentException(NULL_ARGUMENT);
            }
            entityManager.remove(entity);
        } catch (PersistenceException ex) {
            throw new DaoException(ex, DaoError.QUERY_ERROR);
        }
    }

    public void deleteById(K entityId) throws DaoException {
        if (entityId == null) {
            throw new IllegalArgumentException(NULL_ARGUMENT);
        }
        T entity = find(entityId);
        delete(entity);
    }
}