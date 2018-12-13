package com.tlg.core.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;
import java.util.StringJoiner;

@Entity
@NamedQueries({
        @NamedQuery(name = "Shift.endShiftForDriver",
                query = "SELECT s FROM Shift s WHERE s.driver=:driver AND s.shiftEnd IS NULL"),
        @NamedQuery(name = "Shift.getShiftsForDriver",
                query = "SELECT s FROM Shift s WHERE s.driver=:driver"),
        @NamedQuery(name = "Shift.getShiftsForDriverThisMonth",
                query = "SELECT s FROM Shift s WHERE s.driver=:driver AND month(s.shiftEnd) = month(current_date)" +
                        "ORDER BY s.shiftStart ASC")
})
public class Shift extends AbstractEntity {
    private Date shiftStart;
    private Date shiftEnd;
    private Driver driver;

    @Column(name = "shift_start", columnDefinition = "CURRENT_TIMESTAMP", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getShiftStart() {
        return shiftStart;
    }

    public void setShiftStart(Date shiftStart) {
        this.shiftStart = shiftStart;
    }

    @Column(name = "shift_end")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getShiftEnd() {
        return shiftEnd;
    }

    public void setShiftEnd(Date shiftEnd) {
        this.shiftEnd = shiftEnd;
    }

    @ManyToOne
    @JoinColumn(name = "driver_id", referencedColumnName = "id", nullable = false)
    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driverByDriverId) {
        this.driver = driverByDriverId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Shift)) return false;
        Shift shift = (Shift) o;
        return getId() == shift.getId() &&
                getShiftStart().equals(shift.getShiftStart()) &&
                getShiftEnd().equals(shift.getShiftEnd());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, shiftStart, shiftEnd);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Shift.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("shiftStart=" + shiftStart)
                .add("shiftEnd=" + shiftEnd)
                .toString();
    }
}
