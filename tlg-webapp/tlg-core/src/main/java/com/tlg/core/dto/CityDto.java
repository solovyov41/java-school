package com.tlg.core.dto;

import com.tlg.core.validation.groups.EditChecks;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CityDto {
    @NotEmpty(message = "validation.city.name.notempty")
    @Size(min = 2, max = 255, groups = EditChecks.class)
    private String name;
    @NotNull(groups = EditChecks.class)
    private Double longitude;
    @NotNull(groups = EditChecks.class)
    private Double latitude;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
}
