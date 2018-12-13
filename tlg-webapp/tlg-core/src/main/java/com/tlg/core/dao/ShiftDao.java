package com.tlg.core.dao;

import com.tlg.core.dao.exception.DaoException;
import com.tlg.core.entity.Driver;
import com.tlg.core.entity.Shift;

import java.util.List;

public interface ShiftDao extends GenericDao<Shift, Long> {

    Shift endShiftForDriver(Driver driver) throws DaoException;

    List<Shift> getShiftsForDriver(Driver driver) throws DaoException;

    int getWorkedTimeInCurrentMonth(Driver driver) throws DaoException;
}
