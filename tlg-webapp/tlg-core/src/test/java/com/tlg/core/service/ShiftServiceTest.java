package com.tlg.core.service;

import com.tlg.core.dao.ShiftDao;
import com.tlg.core.dao.exception.DaoException;
import com.tlg.core.entity.Driver;
import com.tlg.core.entity.Shift;
import com.tlg.core.service.exceptions.ServiceException;
import com.tlg.core.service.impl.ShiftServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Date;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ShiftServiceTest {
    private static final String NO_SERVICE_EX = "Service exception was not thrown";

    @Mock
    private ShiftDao shiftDao;

    @InjectMocks
    private ShiftServiceImpl ss;

    private Driver tDriver;

    @Before
    /* Initialized mocks */
    public void setup() {
        tDriver = new Driver();
    }

    @Test
    public void startShift_happyPeth() throws DaoException {
        doAnswer(invocation -> {
            Shift createdShift = invocation.getArgument(0);

            assertNotNull(createdShift);
            assertNotNull(createdShift.getDriver());
            assertNotNull(createdShift.getShiftStart());
            return null;
        }).when(shiftDao).create(any(Shift.class));

        try {
            ss.startShift(tDriver);
        } catch (ServiceException ex) {
            fail(ex.getMessage());
        }

        verify(shiftDao, times(1)).create(any(Shift.class));
    }

    @Test
    public void endShift_happyPath() throws DaoException {
        Shift existedShift = new Shift();
        existedShift.setShiftStart(new Date());
        existedShift.setDriver(tDriver);

        doReturn(existedShift).when(shiftDao).endShiftForDriver(any(Driver.class));

        doAnswer(invocation -> {
            Shift shift = invocation.getArgument(0);

            assertNotNull(shift);
            assertNotNull(shift.getDriver());
            assertTrue(existedShift.getShiftStart().before(shift.getShiftEnd()));
            return null;
        }).when(shiftDao).update(any(Shift.class));

        try {
            ss.endShift(tDriver);
        } catch (ServiceException ex) {
            fail(ex.getMessage());
        }

        verify(shiftDao, times(1)).endShiftForDriver(any(Driver.class));
        verify(shiftDao, times(1)).update(any(Shift.class));
    }

    @Test
    public void startShift_daoThrowEx_expectedServiceEx() throws DaoException {
        doThrow(DaoException.class).when(shiftDao).create(any(Shift.class));
        try {
            ss.startShift(tDriver);
            fail(NO_SERVICE_EX);
        } catch (ServiceException ex) {
            //expected
        }
    }

    @Test
    public void endShift_daoThrowEx_onUpdate_expectedServiceEx() throws DaoException {
        Shift existedShift = new Shift();
        existedShift.setDriver(tDriver);
        existedShift.setShiftStart(new Date());

        doThrow(DaoException.class).when(shiftDao).update(any(Shift.class));
        when(shiftDao.endShiftForDriver(any(Driver.class))).thenReturn(existedShift);

        try {
            ss.endShift(tDriver);
            fail(NO_SERVICE_EX);
        } catch (ServiceException ex) {
            //expected
        }
    }
    @Test
    public void endShift_daoThrowEx_onFindDriver_expectedServiceEx() throws DaoException {
        doThrow(DaoException.class).when(shiftDao).endShiftForDriver(any(Driver.class));

        try {
            ss.endShift(tDriver);
            fail(NO_SERVICE_EX);
        } catch (ServiceException ex) {
            //expected
        }
    }
}