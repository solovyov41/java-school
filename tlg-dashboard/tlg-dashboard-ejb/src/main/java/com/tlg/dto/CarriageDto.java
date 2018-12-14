package com.tlg.dto;


import com.tlg.model.Driver;
import com.tlg.model.Vehicle;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class CarriageDto {

    private String uniqueNumber;
    private String status;
    private String customerName;
    private Date initiateDate;

    private List<CargoDto> cargoes;
    private List<WaypointDto> waypoints;
    private List<DriverDto> drivers;
    private Vehicle vehicle;


    public String getUniqueNumber() {
        return uniqueNumber;
    }

    public void setUniqueNumber(String uniqueNumber) {
        this.uniqueNumber = uniqueNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Date getInitiateDate() {
        return initiateDate;
    }

    public void setInitiateDate(Date initiateDate) {
        this.initiateDate = initiateDate;
    }


    public List<CargoDto> getCargoes() {
        return cargoes;
    }

    public void setCargoes(List<CargoDto> cargoes) {
        this.cargoes = cargoes;
    }

    public List<WaypointDto> getWaypoints() {
        return waypoints;
    }

    public void setWaypoints(List<WaypointDto> waypoints) {
        this.waypoints = waypoints;
    }

    public List<DriverDto> getDrivers() {
        return drivers;
    }

    public void setDrivers(List<DriverDto> drivers) {
        this.drivers = drivers;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarriageDto carriage = (CarriageDto) o;
        return uniqueNumber.equals(carriage.uniqueNumber);
    }

    public int hashCode() {
        return Objects.hash(uniqueNumber);
    }
}
