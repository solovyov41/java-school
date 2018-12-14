package com.tlg.web.controller.rest;

import com.tlg.core.dto.CarriageDto;
import com.tlg.core.dto.DriverStatistics;
import com.tlg.core.dto.VehicleStatistics;
import com.tlg.core.service.CarriageService;
import com.tlg.core.service.DriverService;
import com.tlg.core.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rest")
public class ScoreboardController {

    @Autowired
    VehicleService vehicleService;

    @Autowired
    DriverService driverService;

    @Autowired
    CarriageService carriageService;

    @GetMapping("/carriage")
    public List<CarriageDto> getLastCarriages(@RequestParam(name = "number", required = false) Integer number) {
        if (number == null) {
            number = 10;
        }
        return carriageService.getLastCarriages(number);
    }

    @GetMapping("/driver/stat")
    public DriverStatistics getDriverStat() {
        return driverService.getDriverStatistics();
    }

    @GetMapping("/vehicle/stat")
    public VehicleStatistics getVehicleStat() {
        return vehicleService.getVehicleStatistics();
    }
}
