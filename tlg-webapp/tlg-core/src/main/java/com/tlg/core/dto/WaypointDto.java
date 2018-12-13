package com.tlg.core.dto;

import com.tlg.core.validation.annotations.CityCoordinatesExist;
import com.tlg.core.validation.annotations.CityNameNotEmpty;
import com.tlg.core.validation.groups.EditChecks;
import com.tlg.core.validation.groups.RegistrationChecks;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

public class WaypointDto {

    @NotNull(groups = RegistrationChecks.class)
    private Integer position;
    private boolean isVisited;

    @Null(groups = RegistrationChecks.class)
    private CarriageDto carriage;

    @NotNull(groups = RegistrationChecks.class)
    @CityNameNotEmpty(groups = EditChecks.class)
    @CityCoordinatesExist(groups = EditChecks.class)
    private CityDto city;

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public boolean isVisited() {
        return isVisited;
    }

    public void setVisited(boolean visited) {
        isVisited = visited;
    }

    public CarriageDto getCarriage() {
        return carriage;
    }

    public void setCarriage(CarriageDto carriage) {
        this.carriage = carriage;
    }

    public CityDto getCity() {
        return city;
    }

    public void setCity(CityDto city) {
        this.city = city;
    }
}
