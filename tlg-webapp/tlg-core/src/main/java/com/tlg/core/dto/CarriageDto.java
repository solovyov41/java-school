package com.tlg.core.dto;

import com.tlg.core.entity.enums.CarriageStatus;
import com.tlg.core.validation.annotations.CarriageUniqueNumNotExists;
import com.tlg.core.validation.groups.AssignmentChecks;
import com.tlg.core.validation.groups.DatabaseChecks;
import com.tlg.core.validation.groups.EditChecks;
import com.tlg.core.validation.groups.RegistrationChecks;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class CarriageDto {

    @NotEmpty(groups = RegistrationChecks.class)
    @Size(max = 12, groups = RegistrationChecks.class)
    @CarriageUniqueNumNotExists(groups = DatabaseChecks.class)
    private String uniqueNumber;

    @NotNull(groups = EditChecks.class)
    private CarriageStatus status;

    @NotEmpty(groups = EditChecks.class)
    @Size(max = 45, groups = EditChecks.class)
    private String customerName;

    private Date initiateDate;

    @Null(groups = RegistrationChecks.class)
    private Date finishDate;

    private Integer estimatedLeadTime;

    private BigDecimal maxWeight;

    @NotNull(groups = EditChecks.class)
    private List<CargoDto> cargoes;

    @NotNull(groups = RegistrationChecks.class)
    private List<WaypointDto> waypoints;

    @NotNull(groups = AssignmentChecks.class)
    @Size(min = 1, max = 3, groups = AssignmentChecks.class)
    private List<DriverDto> drivers;

    @NotNull(groups = AssignmentChecks.class)
    private VehicleDto vehicle;

    public String getUniqueNumber() {
        return uniqueNumber;
    }

    public void setUniqueNumber(String uniqueNumber) {
        this.uniqueNumber = uniqueNumber;
    }

    public CarriageStatus getStatus() {
        return status;
    }

    public void setStatus(CarriageStatus status) {
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

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public Integer getEstimatedLeadTime() {
        return estimatedLeadTime;
    }

    public void setEstimatedLeadTime(Integer estimatedLeadTime) {
        this.estimatedLeadTime = estimatedLeadTime;
    }

    public BigDecimal getMaxWeight() {
        return maxWeight;
    }

    public void setMaxWeight(BigDecimal maxWeight) {
        this.maxWeight = maxWeight;
    }

    public List<CargoDto> getCargoes() {
        return cargoes;
    }

    public void setCargoes(List<CargoDto> cargoes) {
        this.cargoes = cargoes;
    }

    public List<DriverDto> getDrivers() {
        return drivers;
    }

    public void setDrivers(List<DriverDto> drivers) {
        this.drivers = drivers;
    }

    public VehicleDto getVehicle() {
        return vehicle;
    }

    public void setVehicle(VehicleDto vehicle) {
        this.vehicle = vehicle;
    }

    public List<WaypointDto> getWaypoints() {
        return waypoints;
    }

    public void setWaypoints(List<WaypointDto> waypoints) {
        this.waypoints = waypoints;
    }
}
