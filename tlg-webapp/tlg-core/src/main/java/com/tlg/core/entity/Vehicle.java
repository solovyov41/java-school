package com.tlg.core.entity;

import com.tlg.core.entity.enums.VehicleState;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Objects;
import java.util.StringJoiner;

@Entity
@NamedQueries({
        @NamedQuery(name = "Vehicle.findByLicPlateNum",
                query = "SELECT v FROM Vehicle v WHERE v.licPlateNum=:licPlateNum"),
        @NamedQuery(name = "Vehicle.findByCity",
                query = "SELECT v FROM Vehicle v WHERE v.city=:city"),
        @NamedQuery(name = "Vehicle.findByOrder",
                query = "SELECT v FROM Vehicle v WHERE v.carriage =:carriage"),
        @NamedQuery(name = "Vehicle.findAvailableForOrder",
                query = "SELECT v FROM Vehicle v LEFT JOIN v.carriage WHERE v.carriage.id IS NULL AND v.state = :state AND v.loadCapacity > :maxOrderWeight")
})
public class Vehicle extends AbstractUpdatableEntity {

    private String licPlateNum;
    private BigDecimal loadCapacity;
    private Integer passSeatsNum;
    private VehicleState state;

    private Collection<Driver> coDrivers;
    private City city;
    private Carriage carriage;

    @Column(name = "lic_plate_num", nullable = false)
    public String getLicPlateNum() {
        return licPlateNum;
    }

    public void setLicPlateNum(String licPlateNum) {
        this.licPlateNum = licPlateNum;
    }

    @Column(name = "load_capacity", nullable = false)
    public BigDecimal getLoadCapacity() {
        return loadCapacity;
    }

    public void setLoadCapacity(BigDecimal loadCapacity) {
        this.loadCapacity = loadCapacity;
    }

    @Column(name = "pass_seats_num", nullable = false)
    public Integer getPassSeatsNum() {
        return passSeatsNum;
    }

    public void setPassSeatsNum(Integer passSeatsNum) {
        this.passSeatsNum = passSeatsNum;
    }

    @Column(name = "state", nullable = false)
    @Enumerated(EnumType.STRING)
    public VehicleState getState() {
        return state;
    }

    public void setState(VehicleState state) {
        this.state = state;
    }

    @OneToMany(mappedBy = "vehicle")
    public Collection<Driver> getCoDrivers() {
        return coDrivers;
    }

    public void setCoDrivers(Collection<Driver> driversById) {
        this.coDrivers = driversById;
    }

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "city_id", referencedColumnName = "id", nullable = false)
    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    @OneToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    public Carriage getCarriage() {
        return carriage;
    }

    public void setCarriage(Carriage carriage) {
        this.carriage = carriage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehicle vehicle = (Vehicle) o;
        return getLicPlateNum().equals(vehicle.getLicPlateNum());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLicPlateNum());
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Vehicle.class.getSimpleName() + "[", "]")
                .add(super.toString())
                .add("licPlateNum='" + licPlateNum + "'")
                .add("loadCapacity=" + loadCapacity)
                .add("passSeatsNum=" + passSeatsNum)
                .add("state=" + state)
                .toString();
    }
}
