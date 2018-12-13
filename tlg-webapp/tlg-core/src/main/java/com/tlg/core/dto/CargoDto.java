package com.tlg.core.dto;

import com.tlg.core.entity.enums.CargoState;
import com.tlg.core.validation.groups.EditChecks;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class CargoDto {

    @NotNull(groups = EditChecks.class)
    @NotEmpty(groups = EditChecks.class)
    private String number;

    @NotNull(groups = EditChecks.class)
    @NotEmpty(groups = EditChecks.class)
    private String name;

    private String description;

    @NotNull(groups = EditChecks.class)
    private Integer weight;

    private CargoState state;
    private CarriageDto carriage;

    @NotNull(groups = EditChecks.class)
    private WaypointDto departureWaypoint;

    @NotNull(groups = EditChecks.class)
    private WaypointDto destinationWaypoint;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public CargoState getState() {
        return state;
    }

    public void setState(CargoState state) {
        this.state = state;
    }

    public CarriageDto getCarriage() {
        return carriage;
    }

    public void setCarriage(CarriageDto carriage) {
        this.carriage = carriage;
    }

    public WaypointDto getDepartureWaypoint() {
        return departureWaypoint;
    }

    public void setDepartureWaypoint(WaypointDto departureWaypoint) {
        this.departureWaypoint = departureWaypoint;
    }

    public WaypointDto getDestinationWaypoint() {
        return destinationWaypoint;
    }

    public void setDestinationWaypoint(WaypointDto destinationWaypoint) {
        this.destinationWaypoint = destinationWaypoint;
    }
}
