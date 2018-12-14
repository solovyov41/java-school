package com.tlg.data;

import com.tlg.model.DriverStatistics;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

@ApplicationScoped
public class DriverStatisticsProducer {

    private DriverStatistics driverStat;

    @Inject
    private Event<DriverStatistics> driverStatEvent;

    public DriverStatisticsProducer() {
        this.driverStat = new DriverStatistics();
    }

    @Produces
    @Named
    public DriverStatistics getDriverStat() {
        return driverStat;
    }

    public void setDriverStat(DriverStatistics driverStat) {
        if (driverStat.getTotal() == driverStat.getFree() + driverStat.getInWork()) {
            this.driverStat.setTotal(this.driverStat.getTotal() + driverStat.getTotal());
            this.driverStat.setFree(this.driverStat.getFree() + driverStat.getFree());
            this.driverStat.setInWork(this.driverStat.getInWork() + driverStat.getInWork());
        }
    }

    public void updateDriverStat(DriverStatistics driverStat) {
        setDriverStat(driverStat);
        driverStatEvent.fire(driverStat);
    }

}
