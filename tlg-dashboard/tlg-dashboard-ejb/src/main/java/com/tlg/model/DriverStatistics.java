package com.tlg.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DriverStatistics {

    private int total;

    private int free;

    private int inWork;

    public DriverStatistics() {
        total = 0;
        free = 0;
        inWork = 0;
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
}
