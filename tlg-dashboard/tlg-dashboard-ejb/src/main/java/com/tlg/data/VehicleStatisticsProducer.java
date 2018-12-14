package com.tlg.data;

import com.tlg.model.VehicleStatistics;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

@ApplicationScoped
public class VehicleStatisticsProducer {
    private VehicleStatistics vehicleStat;
    @Inject
    private Event<VehicleStatistics> vehicleStatEvent;

    public VehicleStatisticsProducer() {
        this.vehicleStat = new VehicleStatistics();
    }

    @Produces
    @Named
    public VehicleStatistics getVehicleStat() {
        return vehicleStat;
    }

    public void setVehicleStat(VehicleStatistics vehicleStat) {
        if (vehicleStat.getTotal() == vehicleStat.getFree() + vehicleStat.getInWork() + vehicleStat.getBroken()) {
            this.vehicleStat.setTotal(this.vehicleStat.getTotal() + vehicleStat.getTotal());
            this.vehicleStat.setFree(this.vehicleStat.getFree() + vehicleStat.getFree());
            this.vehicleStat.setInWork(this.vehicleStat.getInWork() + vehicleStat.getInWork());
            this.vehicleStat.setBroken(this.vehicleStat.getBroken() + vehicleStat.getBroken());
        }
    }

    public void updateVehicleStat(VehicleStatistics vehicleStat) {
        setVehicleStat(vehicleStat);
        vehicleStatEvent.fire(vehicleStat);
    }

}
