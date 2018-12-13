package com.tlg.core.service;

import com.tlg.core.dao.exception.DaoException;
import com.tlg.core.entity.Driver;
import com.tlg.core.service.exceptions.ServiceException;
import org.springframework.transaction.annotation.Transactional;

public interface ShiftService {
    @Transactional
    void startShift(Driver driver) throws ServiceException;

    @Transactional
    void endShift(Driver driver) throws ServiceException;

    @Transactional
    int getWorkedTimeInCurrentMonth(Driver driver) throws DaoException;
}
