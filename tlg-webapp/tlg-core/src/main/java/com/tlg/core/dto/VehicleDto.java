package com.tlg.core.dto;

import com.tlg.core.entity.enums.VehicleState;
import com.tlg.core.validation.annotations.CityCoordinatesExist;
import com.tlg.core.validation.annotations.CityNameNotEmpty;
import com.tlg.core.validation.annotations.LicenceNumNotExists;
import com.tlg.core.validation.groups.DatabaseChecks;
import com.tlg.core.validation.groups.EditChecks;
import com.tlg.core.validation.groups.RegistrationChecks;
import com.tlg.core.validation.groups.StatusEditChecks;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;

public class VehicleDto {

    @NotEmpty(groups = StatusEditChecks.class, message = "{validation.field.notEmpty}")
    @Pattern(regexp = "^([A-Z]{2}\\d{5})$",
            groups = RegistrationChecks.class,
            message = "{validation.vehicle.licence.number.pattern}")
    @LicenceNumNotExists(groups = DatabaseChecks.class)
    private String licPlateNum;

    @NotNull(groups = EditChecks.class)
    @DecimalMin(value = "1",
            groups = EditChecks.class,
            message = "{validation.vehicle.min.load}")
    @DecimalMax(value = "25",
            groups = EditChecks.class,
            message = "{validation.vehicle.max.load}")
    private BigDecimal loadCapacity;

    @NotNull(groups = EditChecks.class)
    @Min(value = 1, groups = EditChecks.class, message = "{validation.vehicle.shift.size.min}")
    @Max(value = 3, groups = EditChecks.class, message = "{validation.vehicle.shift.size.max}")
    private Integer passSeatsNum;

    @NotNull(groups = StatusEditChecks.class)
    private VehicleState state;

    @NotNull(groups = EditChecks.class)
    @CityNameNotEmpty(groups = EditChecks.class)
    @CityCoordinatesExist(groups = EditChecks.class)
    private CityDto city;

    @Null(groups = RegistrationChecks.class)
    private List<DriverDto> coDrivers;

    @Null(groups = RegistrationChecks.class)
    private CarriageDto carriage;

    private Integer distanceToCityOrderShipment;

    public String getLicPlateNum() {
        return licPlateNum;
    }

    public void setLicPlateNum(String licPlateNum) {
        this.licPlateNum = licPlateNum;
    }

    public BigDecimal getLoadCapacity() {
        return loadCapacity;
    }

    public void setLoadCapacity(BigDecimal loadCapacity) {
        this.loadCapacity = loadCapacity;
    }

    public Integer getPassSeatsNum() {
        return passSeatsNum;
    }

    public void setPassSeatsNum(Integer passSeatsNum) {
        this.passSeatsNum = passSeatsNum;
    }

    public VehicleState getState() {
        return state;
    }

    public void setState(VehicleState state) {
        this.state = state;
    }

    public List<DriverDto> getCoDrivers() {
        return coDrivers;
    }

    public void setCoDrivers(List<DriverDto> coDrivers) {
        this.coDrivers = coDrivers;
    }

    public CityDto getCity() {
        return city;
    }

    public void setCity(CityDto city) {
        this.city = city;
    }

    public CarriageDto getCarriage() {
        return carriage;
    }

    public void setCarriage(CarriageDto carriage) {
        this.carriage = carriage;
    }

    public Integer getDistanceToCityOrderShipment() {
        return distanceToCityOrderShipment;
    }

    public void setDistanceToCityOrderShipment(Integer distanceToCityOrderShipment) {
        this.distanceToCityOrderShipment = distanceToCityOrderShipment;
    }

}
