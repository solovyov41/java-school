package com.tlg.core.dto;

public class VehicleStatistics {
    private int total;
    private int free;
    private int inWork;
    private int broken;

    public VehicleStatistics() {
        this.total = 0;
        this.free = 0;
        this.inWork = 0;
        this.broken = 0;
    }

    public VehicleStatistics(int free, int inWork, int broken) {
        this.free = free;
        this.inWork = inWork;
        this.broken = broken;
        this.total = free + inWork + broken;
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

    public int getBroken() {
        return broken;
    }

    public VehicleStatistics breakOne(boolean isWorking) {
        if (isWorking) {
            this.inWork -= 1;
        } else {
            this.free -= 1;
        }
        this.broken += 1;
        return this;
    }

    public VehicleStatistics repairOne() {
        this.free += 1;
        this.broken -= 1;
        return this;
    }

    public VehicleStatistics assignOne() {
        this.free -= 1;
        this.inWork += 1;
        return this;
    }

    public VehicleStatistics freeOne() {
        this.free += 1;
        this.inWork -= 1;
        return this;
    }

    public VehicleStatistics removeOne(boolean isBroken) {
        if (isBroken) {
            this.broken -= 1;
        } else {
            this.free -= 1;
        }
        this.total -= 1;
        return this;
    }

    public VehicleStatistics addOne(boolean isBroken) {
        if (isBroken) {
            this.broken += 1;
        } else {
            this.free += 1;
        }
        this.total += 1;
        return this;
    }

}
