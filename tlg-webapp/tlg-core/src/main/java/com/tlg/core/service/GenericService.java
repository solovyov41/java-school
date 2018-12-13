package com.tlg.core.service;

import com.tlg.core.service.exceptions.ServiceException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface GenericService<T, K> {

    @Transactional
    T create(T newObject) throws ServiceException;

    @Transactional
    T findByIdentifier(K identifier) throws ServiceException;

    @Transactional
    List<T> findAll() throws ServiceException;

    @Transactional
    T update(K identifier, T updatedObject) throws ServiceException;

    @Transactional
    boolean delete(K identifier) throws ServiceException;

}
