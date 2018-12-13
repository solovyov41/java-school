package com.tlg.core.entity;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;
import java.util.StringJoiner;

@Entity
@NamedQueries({
        @NamedQuery(name = "City.findByName",
                query = "SELECT c FROM City c WHERE c.name= :name"),
        @NamedQuery(name = "City.findByCoordinates",
                query = "SELECT c FROM City c WHERE c.latitude = :latitude AND c.longitude=:longitude")
})
public class City extends AbstractUpdatableEntity {
    private String name;
    private Double longitude;
    private Double latitude;
    private Collection<Driver> drivers;
    private Collection<Vehicle> vehicles;
    private Collection<Waypoint> waypoints;

    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "longitude", nullable = false)
    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @Column(name = "latitude", nullable = false)
    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    @OneToMany(mappedBy = "city")
    public Collection<Driver> getDrivers() {
        return drivers;
    }

    public void setDrivers(Collection<Driver> driversById) {
        this.drivers = driversById;
    }

    @OneToMany(mappedBy = "city")
    public Collection<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(Collection<Vehicle> vehiclesById) {
        this.vehicles = vehiclesById;
    }

    @OneToMany(mappedBy = "city")
    public Collection<Waypoint> getWaypoints() {
        return waypoints;
    }

    public void setWaypoints(Collection<Waypoint> waypoints) {
        this.waypoints = waypoints;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City city = (City) o;
        return getName().equals(city.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", City.class.getSimpleName() + "[", "]")
                .add(super.toString())
                .add("name='" + name + "'")
                .add("longitude=" + longitude)
                .add("latitude=" + latitude)
                .toString();
    }
}
