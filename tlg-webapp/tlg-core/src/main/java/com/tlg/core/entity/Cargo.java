package com.tlg.core.entity;

import com.tlg.core.entity.enums.CargoState;

import javax.persistence.*;
import java.util.StringJoiner;

@Entity
@NamedQueries({
        @NamedQuery(name = "Cargo.findByOrder",
                query = "SELECT c FROM Cargo c WHERE c.carriage=:carriage"),
        @NamedQuery(name = "Cargo.findByUniqueNumber",
                query = "SELECT c FROM Cargo c WHERE c.number=:uniqueNumber")
})
public class Cargo extends AbstractUpdatableEntity {
    private String number;
    private String name;
    private String description;
    private Integer weight;
    private CargoState state;
    private Carriage carriage;
    private Waypoint departureWaypoint;
    private Waypoint destinationWaypoint;

    @Column(name = "number", nullable = false)
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "weight", nullable = false)
    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    @Basic
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    public CargoState getState() {
        return state;
    }

    public void setState(CargoState state) {
        this.state = state;
    }

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "departure_waypoint_id", referencedColumnName = "id", nullable = false)
    public Waypoint getDepartureWaypoint() {
        return departureWaypoint;
    }

    public void setDepartureWaypoint(Waypoint departureWaypoint) {
        this.departureWaypoint = departureWaypoint;
    }

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "destination_waypoint_id", referencedColumnName = "id", nullable = false)
    public Waypoint getDestinationWaypoint() {
        return destinationWaypoint;
    }

    public void setDestinationWaypoint(Waypoint destinationWaypoint) {
        this.destinationWaypoint = destinationWaypoint;
    }

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "order_id", referencedColumnName = "id", nullable = false)
    public Carriage getCarriage() {
        return carriage;
    }

    public void setCarriage(Carriage carriage) {
        this.carriage = carriage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cargo)) return false;
        Cargo cargo = (Cargo) o;
        return getNumber() != null && getNumber().equals(cargo.getNumber());
    }

    @Override
    public int hashCode() {
        return number.hashCode();
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Cargo.class.getSimpleName() + "[", "]")
                .add(super.toString())
                .add("number=" + number)
                .add("name='" + name + "'")
                .add("description='" + description + "'")
                .add("weight=" + weight)
                .add("state=" + state)
                .toString();
    }
}
