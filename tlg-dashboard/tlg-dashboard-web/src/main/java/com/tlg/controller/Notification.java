package com.tlg.controller;


import com.tlg.model.Carriage;
import com.tlg.model.DriverStatistics;
import com.tlg.model.VehicleStatistics;
import org.omnifaces.cdi.Push;
import org.omnifaces.cdi.PushContext;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.List;

//import javax.faces.push.Push;
//import javax.faces.push.PushContext;

@ApplicationScoped
public class Notification {

    @Inject
    @Push
    private PushContext push;


    public void orderNotification(@Observes List<Carriage> orders) {
        push.send("ordersUpdate");
    }

    public void driverStatNotification(@Observes DriverStatistics driverStatistics) {
        push.send("driverStatUpdate");
    }

    public void vehicleStatNotification(@Observes VehicleStatistics vehicleStatistics) {
        push.send("vehicleStatUpdate");
    }


}