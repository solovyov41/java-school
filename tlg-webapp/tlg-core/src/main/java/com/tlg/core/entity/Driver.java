package com.tlg.core.entity;

import com.tlg.core.entity.enums.DriverStatus;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;
import java.util.StringJoiner;

@Entity
@NamedQueries({
        @NamedQuery(name = "Driver.findByPersonalNum",
                query = "SELECT dr FROM Driver dr WHERE dr.personalNum = :driverPersonalNumber"),
        @NamedQuery(name = "Driver.findByUser",
                query = "SELECT dr FROM Driver dr JOIN FETCH dr.user WHERE dr.user.email=:email"),
        @NamedQuery(name = "Driver.findByCity",
                query = "SELECT dr FROM Driver dr JOIN FETCH dr.city WHERE dr.city.name=:city"),
        @NamedQuery(name = "Driver.findFreeDriversInCity",
                query = "SELECT dr FROM Driver dr JOIN FETCH dr.city WHERE dr.city.name=:city AND dr.carriage IS NULL"),
        @NamedQuery(name = "Driver.findByOrder",
                query = "SELECT dr FROM Driver dr JOIN FETCH dr.carriage WHERE dr.carriage.uniqueNumber=:carriage")
})
public class Driver extends AbstractUpdatableEntity {

    private String personalNum;
    private DriverStatus status;
    private User user;
    private City city;
    private Vehicle vehicle;
    private Carriage carriage;
    private Collection<Shift> shifts;

    @Column(name = "personal_num", nullable = false)
    public String getPersonalNum() {
        return personalNum;
    }

    public void setPersonalNum(String personalNum) {
        this.personalNum = personalNum;
    }

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    public DriverStatus getStatus() {
        return status;
    }

    public void setStatus(DriverStatus status) {
        this.status = status;
    }

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    public User getUser() {
        return user;
    }

    public void setUser(User userByUserId) {
        this.user = userByUserId;
    }

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "city_id", referencedColumnName = "id", nullable = false)
    public City getCity() {
        return city;
    }

    public void setCity(City cityByCityId) {
        this.city = cityByCityId;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "vehicle_id", referencedColumnName = "id")
    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle curVehicleByVehicleId) {
        this.vehicle = curVehicleByVehicleId;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    public Carriage getCarriage() {
        return carriage;
    }

    public void setCarriage(Carriage carriage) {
        this.carriage = carriage;
    }

    @OneToMany(mappedBy = "driver", cascade = CascadeType.ALL)
    public Collection<Shift> getShifts() {
        return shifts;
    }

    public void setShifts(Collection<Shift> shiftsById) {
        this.shifts = shiftsById;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Driver driver = (Driver) o;
        return getPersonalNum().equals(driver.getPersonalNum());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPersonalNum());
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Driver.class.getSimpleName() + "[", "]")
                .add(super.toString())
                .add("personalNum=" + personalNum)
                .add("status=" + status)
                .toString();
    }
}
