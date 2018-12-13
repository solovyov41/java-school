package com.tlg.core.dto;

public class DriverStatistics {
    private int total;
    private int free;
    private int inWork;

    /**
     * Generates empty driver statistics
     */
    public DriverStatistics() {
        this.total = 0;
        this.free = 0;
        this.inWork = 0;
    }


    /**
     * Generates driver statistics with number of
     *
     * @param free   number of free drivers
     * @param inWork number of working drivers
     */
    public DriverStatistics(int free, int inWork) {
        this.free = free;
        this.inWork = inWork;
        this.total = free + inWork;
    }

    public int getTotal() {
        return total;
    }

    public int getFree() {
        return free;
    }

    public int getInWork() {
        return inWork;
    }


    public DriverStatistics assignOne() {
        this.free -= 1;
        this.inWork += 1;
        return this;
    }

    public DriverStatistics freeOne() {
        this.free += 1;
        this.inWork -= 1;
        return this;
    }

    public DriverStatistics removeOne() {
        this.free -= 1;
        this.total -= 1;
        return this;
    }

    public DriverStatistics addOne() {
        this.free += 1;
        this.total += 1;
        return this;
    }

}
