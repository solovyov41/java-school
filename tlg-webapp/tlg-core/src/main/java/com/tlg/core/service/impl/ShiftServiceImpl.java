package com.tlg.core.service.impl;

import com.tlg.core.dao.ShiftDao;
import com.tlg.core.dao.exception.DaoException;
import com.tlg.core.entity.Driver;
import com.tlg.core.entity.Shift;
import com.tlg.core.service.ShiftService;
import com.tlg.core.service.exceptions.ServiceException;
import com.tlg.core.service.exceptions.error.ServiceError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service("shiftService")
public class ShiftServiceImpl implements ShiftService {
    private static final Logger logger = LoggerFactory.getLogger(ShiftServiceImpl.class);

    @Autowired
    ShiftDao shiftDao;

    @Transactional
    @Override
    public void startShift(Driver driver) throws ServiceException {
        Shift newShift = new Shift();
        newShift.setDriver(driver);
        newShift.setShiftStart(new Date());
        try {
            shiftDao.create(newShift);
        } catch (DaoException ex) {
            throw new ServiceException(ex, ServiceError.CANT_CREATE, String.format("shift for driver %s", driver.getPersonalNum()));
        }
    }

    @Transactional
    @Override
    public void endShift(Driver driver) throws ServiceException {
        try {
            Shift shift = shiftDao.endShiftForDriver(driver);
            shift.setShiftEnd(new Date());
            shiftDao.update(shift);
        } catch (DaoException ex) {
            throw new ServiceException(ex, ServiceError.UPDATE_ERROR, "driver", driver.getPersonalNum(), "shift end");
        }
    }

    @Transactional
    @Override
    public int getWorkedTimeInCurrentMonth(Driver driver) throws DaoException {
        return shiftDao.getWorkedTimeInCurrentMonth(driver);
    }
}
