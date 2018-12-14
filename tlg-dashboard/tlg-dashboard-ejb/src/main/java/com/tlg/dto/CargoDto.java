package com.tlg.dto;

public class CargoDto {

    private String number;
    private String name;
    private String description;
    private Integer weight;
    private String state;
    private WaypointDto departureWaypoint;
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


    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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
