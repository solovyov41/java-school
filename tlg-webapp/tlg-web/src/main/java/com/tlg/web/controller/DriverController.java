package com.tlg.web.controller;

import com.tlg.core.dto.DriverDto;
import com.tlg.core.dto.UserDto;
import com.tlg.core.entity.User;
import com.tlg.core.service.DriverService;
import com.tlg.core.service.UserService;
import com.tlg.core.service.exceptions.ServiceException;
import com.tlg.core.validation.groups.RegistrationChecks;
import org.modelmapper.TypeMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/manager/driver")
public class DriverController {

    private static final Logger logger = LoggerFactory.getLogger(DriverController.class);

    private static final String DRIVER_ATR = "driver";

    private static final String DRIVER_DASHBOARD = "/manager/driver/drivers";
    private static final String DRIVER_INFO = "/manager/driver/driver";
    private static final String DRIVER_FORM = "/manager/driver/driverForm";
    private static final String REDIRECT_DRIVER_DASHBOARD = "redirect:/manager/driver";

    @Autowired
    private DriverService driverService;

    @Autowired
    private UserService userService;

    @Autowired
    private TypeMap<UserDto, User> userDtoUserTypeMap;

    @GetMapping
    public String getDriversDashboard(Model model) throws ServiceException {
        model.addAttribute("drivers", driverService.findAll());
        return DRIVER_DASHBOARD;
    }

    @GetMapping("/{personalNum}")
    public String getDriverProfile(@PathVariable String personalNum, Model model) throws ServiceException {
        model.addAttribute(DRIVER_ATR, driverService.findByIdentifier(personalNum));
        return DRIVER_INFO;
    }

    @PostMapping
    public String createDriver(@ModelAttribute(DRIVER_ATR) @Validated(RegistrationChecks.class) DriverDto driverDto,
                               BindingResult bindingResult, Model model) throws ServiceException {
        if (bindingResult.hasErrors()) {
            model.addAttribute("edit", false);
            return DRIVER_FORM;
        }

        driverService.create(driverDto);
        return REDIRECT_DRIVER_DASHBOARD;
    }


    @GetMapping("/new")
    public String createDriver(Model model) throws ServiceException {
        model.addAttribute("availableUsers", userService.findAllNotAssignedDriverUsers());
        model.addAttribute(DRIVER_ATR, driverService.getNewDriver());
        model.addAttribute("edit", false);
        return DRIVER_FORM;
    }

    @GetMapping("/edit/{personalNum}")
    public String getEditDriverPage(@PathVariable String personalNum, Model model) throws ServiceException {
        model.addAttribute("availableUsers", userService.findAllNotAssignedDriverUsers());
        model.addAttribute(DRIVER_ATR, driverService.findByIdentifier(personalNum));
        model.addAttribute("edit", true);
        return DRIVER_FORM;
    }

    @PostMapping("/edit")
    public String editDriver(@ModelAttribute(DRIVER_ATR) DriverDto driverDto,
                             BindingResult bindingResult, Model model) throws ServiceException {
        if (bindingResult.hasErrors()) {
            model.addAttribute("edit", true);
            return DRIVER_FORM;
        }

        driverService.update(driverDto.getPersonalNum(), driverDto);
        return REDIRECT_DRIVER_DASHBOARD;
    }

    @GetMapping("/delete/{personalNum}")
    public String deleteDriver(@PathVariable String personalNum) throws ServiceException {
        driverService.delete(personalNum);
        return REDIRECT_DRIVER_DASHBOARD;
    }

}
