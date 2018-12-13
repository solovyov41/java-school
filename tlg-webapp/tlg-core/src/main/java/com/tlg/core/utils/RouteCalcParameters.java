package com.tlg.core.utils;

public class RouteCalcParameters {
    private int averageSpeed;

    private int monthWorkTime;

    private int dayWorkTime;

    public RouteCalcParameters(int averageSpeed, int monthWorkTime, int dayWorkTime) {
        this.averageSpeed = averageSpeed;
        this.monthWorkTime = monthWorkTime;
        this.dayWorkTime = dayWorkTime;
    }

    public int getAverageSpeed() {
        return averageSpeed;
    }

    public void setAverageSpeed(int averageSpeed) {
        this.averageSpeed = averageSpeed;
    }

    public int getMonthWorkTime() {
        return monthWorkTime;
    }

    public void setMonthWorkTime(int monthWorkTime) {
        this.monthWorkTime = monthWorkTime;
    }

    public int getDayWorkTime() {
        return dayWorkTime;
    }

    public void setDayWorkTime(int dayWorkTime) {
        this.dayWorkTime = dayWorkTime;
    }
}
