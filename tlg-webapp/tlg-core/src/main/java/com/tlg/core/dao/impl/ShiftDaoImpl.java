package com.tlg.core.dao.impl;

import com.tlg.core.dao.ShiftDao;
import com.tlg.core.dao.exception.DaoException;
import com.tlg.core.dao.exception.error.DaoError;
import com.tlg.core.entity.Driver;
import com.tlg.core.entity.Shift;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;

@Repository("shiftDao")
public class ShiftDaoImpl extends AbstractDao<Shift, Long> implements ShiftDao {

    private static final String SHIFT_LIST = "Shift list::{}";

    private static final Logger logger = LoggerFactory.getLogger(ShiftDaoImpl.class);

    public ShiftDaoImpl() {
        super(Shift.class);
    }

    @Override
    public Shift endShiftForDriver(Driver driver) throws DaoException {
        try {
            TypedQuery<Shift> query = entityManager.createNamedQuery("Shift.endShiftForDriver", Shift.class);
            query.setParameter("driver", driver);
            return query.getSingleResult();

        } catch (PersistenceException ex) {
            throw new DaoException(ex, DaoError.QUERY_ERROR);
        }

    }

    @Override
    public List<Shift> getShiftsForDriver(Driver driver) throws DaoException {
        try {
            TypedQuery<Shift> query = entityManager.createNamedQuery("Shift.getShiftsForDriver", Shift.class);
            query.setParameter("driver", driver);
            List<Shift> shifts = query.getResultList();

            logger.info("Shifts list for {}", driver);
            for (Shift shift : shifts) {
                logger.info(SHIFT_LIST, shift);
            }
            return shifts;
        } catch (PersistenceException ex) {
            throw new DaoException(ex, DaoError.QUERY_ERROR);
        }
    }

    @Override
    public int getWorkedTimeInCurrentMonth(Driver driver) throws DaoException {
        try {
            TypedQuery<Shift> query = entityManager.createNamedQuery("Shift.getShiftsForDriverThisMonth", Shift.class);
            query.setParameter("driver", driver);
            List<Shift> shifts = query.getResultList();
            shifts.sort(Comparator.comparing(Shift::getShiftStart));

            LocalDateTime shiftStart;
            LocalDateTime shiftEnd;
            LocalDate now = LocalDate.now();

            int workedHours = 0;

            logger.info("Shifts list for month {}", now.getMonth().name());
            for (int i = 0, l = shifts.size(); i < l; i++) {
                shiftStart = shifts.get(i).getShiftStart().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                shiftEnd = shifts.get(i).getShiftEnd().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

                if (i == 0 && shiftStart.getMonthValue() < now.getMonthValue()) {
                    workedHours += ChronoUnit.HOURS.between(LocalDateTime.of(now.getYear(), now.getMonth(), 1, 0, 0), shiftEnd);
                    continue;
                }

                workedHours += ChronoUnit.HOURS.between(shiftStart, shiftEnd);
                logger.info(SHIFT_LIST, shifts.get(i));
            }
            return workedHours;
        } catch (PersistenceException ex) {
            throw new DaoException(ex, DaoError.QUERY_ERROR);
        }
    }
}
