package com.tlg.core.dao;

import com.tlg.core.dao.exception.DaoException;

import java.io.Serializable;
import java.util.List;

/**
 * Unified interface for managing persistent state of objects
 *
 * @param <T> object type persistence
 * @param <K> primary key type
 */
public interface GenericDao<T, K extends Serializable> {

    /**
     * Creates a new record and its corresponding object.
     */
    void create(final T object) throws DaoException;

    /**
     * Returns the object of the corresponding entry with the primary key or null.
     */
    T find(final K id) throws DaoException;

    /**
     * Returns a list of objects corresponding to all entries in the database.
     */
    List<T> findAll() throws DaoException;

    /**
     * Stores the state of an object in a database
     */
    void update(final T object) throws DaoException;

    /**
     * Deletes an object entry from the database.
     */
    void delete(final T object) throws DaoException;

    /**
     * Deletes an object entry bu id from the database.
     */
    void deleteById(final K id) throws DaoException;
}