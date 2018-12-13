package com.tlg.web.controller;

import com.tlg.core.dto.CityDto;
import com.tlg.core.dto.VehicleDto;
import com.tlg.core.entity.enums.VehicleState;
import com.tlg.core.service.VehicleService;
import com.tlg.core.service.exceptions.ServiceException;
import com.tlg.core.validation.groups.EditChecks;
import com.tlg.core.validation.groups.OrderedRegistrationChecks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/manager/vehicle")
public class VehicleController {

    private static final Logger logger = LoggerFactory.getLogger(VehicleController.class);

    private static final String VEHICLE_ATR = "vehicle";

    private static final String VEHICLE_DASHBOARD = "/manager/vehicle/vehicles";
    private static final String VEHICLE_FORM = "/manager/vehicle/vehicleForm";
    private static final String VEHICLE_INFO = "/manager/vehicle/vehicle";
    private static final String REDIRECT_VEHICLE_DASHBOARD = "redirect:/manager/vehicle";

    @Autowired
    VehicleService vehicleService;

    @GetMapping
    public String getVehicleDashboard(Model model) throws ServiceException {
        model.addAttribute("vehicles", vehicleService.findAll());
        return VEHICLE_DASHBOARD;
    }

    @GetMapping("/{licenceNum}")
    public String getVehicleInfo(@PathVariable String licenceNum, Model model) throws ServiceException {
        model.addAttribute(VEHICLE_ATR, vehicleService.findByIdentifier(licenceNum));
        return VEHICLE_INFO;
    }

    @PostMapping
    public String createVehicle(@ModelAttribute(VEHICLE_ATR) @Validated(OrderedRegistrationChecks.class) VehicleDto vehicleDto,
                                BindingResult bindingResult, Model model) throws ServiceException {
        if (bindingResult.hasErrors()) {
            model.addAttribute("edit", false);
            return VEHICLE_FORM;
        }

        vehicleService.create(vehicleDto);
        return REDIRECT_VEHICLE_DASHBOARD;
    }

    @PostMapping("/changeStatus")
    public String changeVehicleStatus(@ModelAttribute(VEHICLE_ATR) VehicleDto vehicleDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return VEHICLE_FORM;
        }

        try {
            vehicleService.changeVehicleState(vehicleDto.getLicPlateNum(), vehicleDto.getState());
        } catch (ServiceException ex) {
            logger.warn(ex.getError().getMessage());
        }

        return REDIRECT_VEHICLE_DASHBOARD;
    }

    @GetMapping("/new")
    public String createVehicle(Model model) {
        VehicleDto vehicleDto = new VehicleDto();
        vehicleDto.setCity(new CityDto());
        vehicleDto.setState(VehicleState.OK);
        model.addAttribute(VEHICLE_ATR, vehicleDto);
        model.addAttribute("edit", false);
        return VEHICLE_FORM;
    }

    @GetMapping("/edit/{licPlateNum}")
    public String getEditVehiclePage(@PathVariable String licPlateNum, Model model) throws ServiceException {
        VehicleDto vehicleDto = vehicleService.findByIdentifier(licPlateNum);
        model.addAttribute(VEHICLE_ATR, vehicleDto);
        model.addAttribute("edit", true);
        return VEHICLE_FORM;
    }

    @PostMapping("/edit")
    public String editVehicle(@ModelAttribute(VEHICLE_ATR) @Validated(EditChecks.class) VehicleDto vehicleDto,
                              BindingResult bindingResult, Model model) throws ServiceException {
        if (bindingResult.hasErrors()) {
            model.addAttribute("edit", true);
            return VEHICLE_FORM;
        }

        vehicleService.update(vehicleDto.getLicPlateNum(), vehicleDto);
        return REDIRECT_VEHICLE_DASHBOARD;
    }

    @GetMapping("/delete/{licPlateNum}")
    public String deleteDriver(@PathVariable String licPlateNum) throws ServiceException {
        vehicleService.delete(licPlateNum);
        return REDIRECT_VEHICLE_DASHBOARD;
    }
}
