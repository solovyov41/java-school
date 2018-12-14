package com.tlg.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class VehicleStatistics {

    private int total;

    private int free;

    private int inWork;

    private int broken;

    public VehicleStatistics() {
        total = 0;
        free = 0;
        inWork = 0;
        broken = 0;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getFree() {
        return free;
    }

    public void setFree(int free) {
        this.free = free;
    }

    public int getInWork() {
        return inWork;
    }

    public void setInWork(int inWork) {
        this.inWork = inWork;
    }

    public int getBroken() {
        return broken;
    }

    public void setBroken(int broken) {
        this.broken = broken;
    }
}
