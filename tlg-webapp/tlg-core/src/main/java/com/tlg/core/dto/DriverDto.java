package com.tlg.core.dto;

import com.tlg.core.entity.User;
import com.tlg.core.entity.enums.DriverStatus;
import com.tlg.core.validation.annotations.CityCoordinatesExist;
import com.tlg.core.validation.annotations.CityNameNotEmpty;
import com.tlg.core.validation.annotations.DriverPersonalNumNotExists;
import com.tlg.core.validation.annotations.UserAssigned;
import com.tlg.core.validation.groups.EditChecks;
import com.tlg.core.validation.groups.RegistrationChecks;
import com.tlg.core.validation.groups.StatusEditChecks;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Collection;

public class DriverDto {

    @NotEmpty(groups = StatusEditChecks.class)
    @DriverPersonalNumNotExists(groups = RegistrationChecks.class)
    private String personalNum;

    @NotNull(groups = StatusEditChecks.class)
    private DriverStatus status;

    @UserAssigned(groups = EditChecks.class)
    private User user;

    @NotNull(groups = EditChecks.class)
    @CityCoordinatesExist(groups = EditChecks.class)
    @CityNameNotEmpty(groups = EditChecks.class)
    private CityDto city;

    @Null(groups = RegistrationChecks.class)
    private VehicleDto vehicle;

    @Null(groups = RegistrationChecks.class)
    private CarriageDto carriage;

    @Null(groups = RegistrationChecks.class)
    private Collection<ShiftDto> shiftDtos;

    public String getPersonalNum() {
        return personalNum;
    }

    public void setPersonalNum(String personalNum) {
        this.personalNum = personalNum;
    }

    public DriverStatus getStatus() {
        return status;
    }

    public void setStatus(DriverStatus status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public CityDto getCity() {
        return city;
    }

    public void setCity(CityDto city) {
        this.city = city;
    }

    public VehicleDto getVehicle() {
        return vehicle;
    }

    public void setVehicle(VehicleDto vehicle) {
        this.vehicle = vehicle;
    }

    public CarriageDto getCarriage() {
        return carriage;
    }

    public void setCarriage(CarriageDto carriage) {
        this.carriage = carriage;
    }

    public Collection<ShiftDto> getShifts() {
        return shiftDtos;
    }

    public void setShifts(Collection<ShiftDto> shiftDtos) {
        this.shiftDtos = shiftDtos;
    }
}
