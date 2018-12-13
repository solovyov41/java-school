package com.tlg.core.dto;

import com.tlg.core.entity.Driver;

import java.util.Date;

public class ShiftDto {

    private Date shiftStart;
    private Date shiftEnd;
    private Driver driver;

    public Date getShiftStart() {
        return shiftStart;
    }

    public void setShiftStart(Date shiftStart) {
        this.shiftStart = shiftStart;
    }

    public Date getShiftEnd() {
        return shiftEnd;
    }

    public void setShiftEnd(Date shiftEnd) {
        this.shiftEnd = shiftEnd;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }
}
