package com.tlg.model;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "vehicle")
public class Vehicle extends AbstractEntity {
    private String licPlateNum;

    private BigDecimal loadCapacity;

    private Integer passSeatsNum;

    private String state;

    private Carriage carriage;

    @Column(nullable = false)
    public String getLicPlateNum() {
        return licPlateNum;
    }

    public void setLicPlateNum(String licPlateNum) {
        this.licPlateNum = licPlateNum;
    }

    @Column(nullable = false)
    public BigDecimal getLoadCapacity() {
        return loadCapacity;
    }

    public void setLoadCapacity(BigDecimal loadCapacity) {
        this.loadCapacity = loadCapacity;
    }

    @Column(nullable = false)
    public Integer getPassSeatsNum() {
        return passSeatsNum;
    }

    public void setPassSeatsNum(Integer passSeatsNum) {
        this.passSeatsNum = passSeatsNum;
    }

    @Column(nullable = false)
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @OneToOne
    @JoinColumn(nullable = false)
    public Carriage getCarriage() {
        return carriage;
    }

    public void setCarriage(Carriage carriage) {
        this.carriage = carriage;
    }
}
