package com.tlg.core.entity;

import com.tlg.core.entity.enums.CarriageStatus;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

@Entity
@NamedQueries({
        @NamedQuery(name = "Order.findByUniqueNumber",
                query = "SELECT o FROM Carriage o WHERE o.uniqueNumber = :uniqueNumber"),
        @NamedQuery(name = "Carriage.findByVehicle",
                query = "SELECT o FROM Carriage o WHERE o.vehicle=:vehicle"),
        @NamedQuery(name = "Carriage.findWithoutDriversByCity",
                query = "SELECT o FROM Carriage o LEFT JOIN  o.waypoints w " +
                        "WHERE w.position = 1 AND w.city = :city AND w.city.drivers IS EMPTY"),
        @NamedQuery(name = "Carriage.findWithoutAssignment",
                query = "SELECT o FROM Carriage o WHERE (o.vehicle IS NULL) OR (o.drivers IS EMPTY)"),
        @NamedQuery(name = "Carriage.findByDepartureCity",
                query = "SELECT o FROM Carriage o LEFT JOIN o.waypoints w WHERE w.position= 1 AND w.city= :city"),
        @NamedQuery(name = "Carriage.findInProcess",
                query = "SELECT o FROM Carriage o WHERE o.vehicle IS NOT NULL AND o.drivers IS NOT EMPTY"),
        @NamedQuery(name = "Carriage.findLast",
                query = "SELECT o FROM Carriage o ORDER BY o.initiateDate DESC")
})
public class Carriage extends AbstractUpdatableEntity {
    private String uniqueNumber;
    private CarriageStatus status;
    private String customerName;
    private Date initiateDate;
    private Date finishDate;
    private Integer estimatedLeadTime;
    private List<Cargo> cargoes;
    private List<Driver> drivers;
    private List<Waypoint> waypoints;
    private Vehicle vehicle;

    @Column(name = "unique_number", nullable = false)
    public String getUniqueNumber() {
        return uniqueNumber;
    }

    public void setUniqueNumber(String uniqueNumber) {
        this.uniqueNumber = uniqueNumber;
    }

    @Column(name = "carriage_status")
    @Enumerated(EnumType.STRING)
    public CarriageStatus getStatus() {
        return status;
    }

    public void setStatus(CarriageStatus status) {
        this.status = status;
    }

    @Column(name = "customer_name", nullable = false)
    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    @Column(name = "initiate_date", nullable = false)
    public Date getInitiateDate() {
        return initiateDate;
    }

    public void setInitiateDate(Date initiateDate) {
        this.initiateDate = initiateDate;
    }

    @Column(name = "finish_date")
    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    @Column(name = "estimated_lead_time_hours", nullable = false)
    public Integer getEstimatedLeadTime() {
        return estimatedLeadTime;
    }

    public void setEstimatedLeadTime(Integer estimatedLeadTime) {
        this.estimatedLeadTime = estimatedLeadTime;
    }

    @OneToMany(mappedBy = "carriage", cascade = CascadeType.ALL)
    public List<Cargo> getCargoes() {
        return cargoes;
    }

    public void setCargoes(List<Cargo> cargosById) {
        this.cargoes = cargosById;
    }

    @OneToMany(mappedBy = "carriage")
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

    @OneToMany(mappedBy = "carriage", cascade = CascadeType.ALL)
    public List<Waypoint> getWaypoints() {
        return waypoints;
    }

    public void setWaypoints(List<Waypoint> waypoints) {
        this.waypoints = waypoints;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Carriage carriage = (Carriage) o;
        return getUniqueNumber().equals(carriage.getUniqueNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUniqueNumber());
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Carriage.class.getSimpleName() + "[", "]")
                .add(super.toString())
                .add("uniqueNumber='" + uniqueNumber + "'")
                .add("status=" + status)
                .add("customerName='" + customerName + "'")
                .add("initiateDate=" + initiateDate)
                .add("finishDate=" + finishDate)
                .toString();
    }
}
