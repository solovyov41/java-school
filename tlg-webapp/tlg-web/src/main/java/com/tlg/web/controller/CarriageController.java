package com.tlg.web.controller;

import com.tlg.core.dto.*;
import com.tlg.core.service.CargoService;
import com.tlg.core.service.CarriageService;
import com.tlg.core.service.DriverService;
import com.tlg.core.service.VehicleService;
import com.tlg.core.service.exceptions.ServiceException;
import com.tlg.core.validation.groups.AssignmentChecks;
import com.tlg.core.validation.groups.RegistrationChecks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Controller
@RequestMapping("/manager/carriage")
public class CarriageController {
    private static final String CARRIAGE_FORM = "/manager/carriage/carriageForm";
    private static final String CARRIAGE_DASHBOARD = "/manager/carriage/carriages";
    private static final String CARRIAGE_INFO = "/manager/carriage/carriage";
    private static final String CARRIAGE_VEHICLE_AND_DRIVERS = "/manager/carriage/vehicleDrivers";
    private static final String REDIRECT_CARRIAGE_INFO = "redirect:/manager/carriage/%s";
    private static final String REDIRECT_CARRIAGE_DASHBOARD = "redirect:/manager/carriage";

    private static final String CARRIAGE_ATR = "carriage";

    @Autowired
    private CarriageService carriageService;

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private DriverService driverService;

    @Autowired
    private CargoService cargoService;

    /**
     * @param model model for displaying jsp page
     * @return Dashboard page for carriages
     * @throws ServiceException
     */
    @GetMapping
    public String getCarriageDashboard(Model model) throws ServiceException {
        model.addAttribute("OrderList", carriageService.findAll());
        return CARRIAGE_DASHBOARD;
    }

    /**
     * @param uniqueNumber carriage unique number
     * @param model        model for displaying jsp page
     * @return Carriage information page with actions: create route and assign vehicle and drivers
     * @throws ServiceException
     */
    @GetMapping("/{uniqueNumber}")
    public String getCarriageInfo(@PathVariable String uniqueNumber, Model model) throws ServiceException {
        model.addAttribute(CARRIAGE_ATR, carriageService.findByIdentifier(uniqueNumber));
        return CARRIAGE_INFO;
    }


    @PostMapping
    public String createCarriage(@ModelAttribute(CARRIAGE_ATR) @Validated(RegistrationChecks.class) CarriageDto carriageDto,
                                 BindingResult bindingResult, Model model) throws ServiceException {
        if (bindingResult.hasErrors()) {
            model.addAttribute("hasError", true);
            return CARRIAGE_FORM;
        }

        carriageService.create(carriageDto);
        model.addAttribute(carriageDto);
        return String.format(REDIRECT_CARRIAGE_INFO, carriageDto.getUniqueNumber());
    }

    @GetMapping("/new")
    public String getCreateCarriagePage(Model model) {

        CarriageDto carriageDto = carriageService.getNewCarriage();
        CargoDto cargoDto = cargoService.getNewCargo();
        carriageDto.getCargoes().add(cargoDto);
        carriageDto.getWaypoints().add(new WaypointDto());
        carriageDto.getWaypoints().add(new WaypointDto());

        model.addAttribute(CARRIAGE_ATR, carriageDto);
        model.addAttribute("edit", false);
        return CARRIAGE_FORM;
    }

    @GetMapping("/edit/{uniqueNumber}")
    public String getEditCarriagePage(@PathVariable String uniqueNumber, Model model) throws ServiceException {
        CarriageDto carriageDto = carriageService.findByIdentifier(uniqueNumber);

        model.addAttribute(CARRIAGE_ATR, carriageDto);
        model.addAttribute("edit", true);
        return CARRIAGE_FORM;
    }

    @PostMapping("/edit")
    public String updateCarriage(@ModelAttribute(CARRIAGE_ATR) @Validated(RegistrationChecks.class) CarriageDto carriageDto,
                                 BindingResult bindingResult, Model model) throws ServiceException {
        if (bindingResult.hasErrors()) {
            model.addAttribute("edit", true);
            return CARRIAGE_FORM;
        }

        if (carriageService.update(carriageDto.getUniqueNumber(), carriageDto) == null) {
            return REDIRECT_CARRIAGE_DASHBOARD;
        }

        model.addAttribute(carriageDto);
        return String.format(REDIRECT_CARRIAGE_INFO, carriageDto.getUniqueNumber());
    }

    @GetMapping("/delete/{uniqueNumber}")
    public String deleteCarriage(@PathVariable String uniqueNumber) throws ServiceException {
        carriageService.delete(uniqueNumber);
        return REDIRECT_CARRIAGE_DASHBOARD;
    }


    @GetMapping("/{uniqueNumber}/vehicle")
    public String getVehicleAssignPage(@PathVariable String uniqueNumber, Model model) throws ServiceException {
        CarriageDto carriageDto = carriageService.findByIdentifier(uniqueNumber);

        Map<VehicleDto, List<DriverDto>> vehicleDriversMap = new TreeMap<>((v1, v2) -> {
            int distComp = v1.getDistanceToCityOrderShipment().compareTo(v2.getDistanceToCityOrderShipment());
            if (distComp != 0) {
                return distComp;
            }
            return v1.getLicPlateNum().compareTo(v2.getLicPlateNum());
        });
        List<DriverDto> drivers;
        for (VehicleDto vehicleDto : vehicleService.findAvailableForOrder(carriageDto)) {
            drivers = driverService.findDriverForOrder(carriageDto, vehicleDto);
            if (!drivers.isEmpty()) {
                vehicleDriversMap.put(vehicleDto, drivers);
            }
        }

        model.addAttribute(CARRIAGE_ATR, carriageDto);
        model.addAttribute("vehicleDriversMap", vehicleDriversMap);
        return CARRIAGE_VEHICLE_AND_DRIVERS;
    }

    @PostMapping("/{uniqueNumber}/vehicle")
    public String assignVehicleAndDrivers(@PathVariable String uniqueNumber,
                                          @ModelAttribute(CARRIAGE_ATR)
                                          @Validated(AssignmentChecks.class) CarriageDto carriageDto,
                                          BindingResult bindingResult) throws ServiceException {
        if (bindingResult.hasErrors()) {
            return String.format("redirect:/manager/carriage/%s/vehicle", uniqueNumber);
        }


        if (carriageService.update(uniqueNumber, carriageDto) == null) {
            return REDIRECT_CARRIAGE_DASHBOARD;
        }

        return String.format(REDIRECT_CARRIAGE_INFO, carriageDto.getUniqueNumber());
    }


}

