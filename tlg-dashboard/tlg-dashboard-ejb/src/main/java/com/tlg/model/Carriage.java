package com.tlg.model;


import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@XmlRootElement
@Table(name = "carriage")
@NamedQueries({
        @NamedQuery(name = "Carriage.findAllOrderedByDate",
                query = "SELECT c FROM Carriage c ORDER BY c.initiateDate"),
        @NamedQuery(name = "Carriage.findByUniqueNumber",
                query = "SELECT c FROM Carriage c WHERE c.uniqueNumber = :uniqueNumber")
})
public class Carriage extends AbstractEntity {

    private String uniqueNumber;
    private String status;
    private String customerName;
    private Date initiateDate;

    private List<Cargo> cargoes;
    private List<Waypoint> waypoints;
    private List<Driver> drivers;
    private Vehicle vehicle;

    @Column(nullable = false)
    public String getUniqueNumber() {
        return uniqueNumber;
    }

    public void setUniqueNumber(String uniqueNumber) {
        this.uniqueNumber = uniqueNumber;
    }

    @Column
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Column(nullable = false)
    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    @Column(nullable = false)
    public Date getInitiateDate() {
        return initiateDate;
    }

    public void setInitiateDate(Date initiateDate) {
        this.initiateDate = initiateDate;
    }

    @OneToMany(mappedBy = "carriage", cascade = CascadeType.ALL)
    public List<Cargo> getCargoes() {
        return cargoes;
    }

    public void setCargoes(List<Cargo> cargoes) {
        this.cargoes = cargoes;
    }

    @OneToMany(mappedBy = "carriage", cascade = CascadeType.ALL)
    public List<Waypoint> getWaypoints() {
        return waypoints;
    }

    public void setWaypoints(List<Waypoint> waypoints) {
        this.waypoints = waypoints;
    }

    @OneToMany(mappedBy = "carriage", cascade = CascadeType.ALL)
    public List<Driver> getDrivers() {
        return drivers;
    }

    public void setDrivers(List<Driver> drivers) {
        this.drivers = drivers;
    }

    @OneToOne(mappedBy = "carriage")
    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Carriage carriage = (Carriage) o;
        return uniqueNumber.equals(carriage.uniqueNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uniqueNumber);
    }
}
