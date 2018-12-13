package com.tlg.core.entity;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Waypoint extends AbstractUpdatableEntity {
    private Integer position;
    private boolean isVisited;
    private Carriage carriage;
    private City city;
    private Collection<Cargo> fromThisWp;
    private Collection<Cargo> toThisWp;

    @Column(name = "posInRoute", nullable = false)
    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    @Column(name = "is_visited")
    public boolean getVisited() {
        return isVisited;
    }

    public void setVisited(boolean visited) {
        isVisited = visited;
    }

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id", nullable = false)
    public Carriage getCarriage() {
        return carriage;
    }

    public void setCarriage(Carriage carriage) {
        this.carriage = carriage;
    }

    @ManyToOne
    @JoinColumn(name = "city_id", referencedColumnName = "id", nullable = false)
    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    @OneToMany(mappedBy = "departureWaypoint", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    public Collection<Cargo> getFromThisWp() {
        return fromThisWp;
    }

    public void setFromThisWp(Collection<Cargo> fromThisWp) {
        this.fromThisWp = fromThisWp;
    }

    @OneToMany(mappedBy = "destinationWaypoint", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    public Collection<Cargo> getToThisWp() {
        return toThisWp;
    }

    public void setToThisWp(Collection<Cargo> toThisWp) {
        this.toThisWp = toThisWp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Waypoint waypoint = (Waypoint) o;
        return getPosition().equals(waypoint.getPosition()) &&
                getCarriage().equals(waypoint.getCarriage());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPosition(), getCarriage());
    }
}
