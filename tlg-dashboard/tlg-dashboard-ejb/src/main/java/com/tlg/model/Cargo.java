package com.tlg.model;

import javax.persistence.*;

@Entity
@Table(name = "cargo")
public class Cargo extends AbstractEntity {

    private String number;
    private String name;
    private String description;
    private Integer weight;
    private String state;
    private String departureCity;
    private String destinationCity;

    private Carriage carriage;

    @Column(nullable = false)
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Column(nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(nullable = false)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(nullable = false)
    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    @Column(nullable = false)
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Column(nullable = false)
    public String getDepartureCity() {
        return departureCity;
    }

    @Column(nullable = false)
    public void setDepartureCity(String departureCity) {
        this.departureCity = departureCity;
    }

    public String getDestinationCity() {
        return destinationCity;
    }

    public void setDestinationCity(String destinationCity) {
        this.destinationCity = destinationCity;
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
