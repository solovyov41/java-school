package com.tlg.model;

import javax.persistence.*;

@Entity
@Table(name = "waypoints")
public class Waypoint extends AbstractEntity {
    private Integer position;
    private String city;

    private Carriage carriage;

    @Column(nullable = false)
    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    @Column(nullable = false)
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @ManyToOne
    @JoinColumn(nullable = false)
    public Carriage getCarriage() {
        return carriage;
    }

    public void setCarriage(Carriage carriage) {
        this.carriage = carriage;
    }
}
