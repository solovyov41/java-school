package com.tlg.web.controller;

import com.tlg.core.dto.DriverDto;
import com.tlg.core.dto.UserDto;
import com.tlg.core.entity.User;
import com.tlg.core.service.CarriageService;
import com.tlg.core.service.DriverService;
import com.tlg.core.service.exceptions.ServiceException;
import com.tlg.core.service.exceptions.error.ServiceError;
import org.modelmapper.TypeMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/driver")
public class DeliveryController {

    private static final Logger logger = LoggerFactory.getLogger(DeliveryController.class);
    public static final String REDIRECT_ACCOUNT_DASHBOARD = "redirect:/account/dashboard";

    @Autowired
    CarriageService carriageService;

    @Autowired
    DriverService driverService;

    @Autowired
    TypeMap<UserDto, User> userDtoUserTypeMap;

    @GetMapping("/delivery/{carriageNumber}/arrival/{waypointPosition}")
    public String checkIn(@PathVariable String carriageNumber,
                          @PathVariable Integer waypointPosition,
                          @AuthenticationPrincipal UserDto currentUser) throws ServiceException {

        User user = userDtoUserTypeMap.map(currentUser);
        carriageService.checkInArrival(carriageNumber, waypointPosition, user);

        return REDIRECT_ACCOUNT_DASHBOARD;
    }

    @PostMapping("/changeStatus")
    public String changeDriverStatus(@ModelAttribute("driver") DriverDto driverDto,
                                     @AuthenticationPrincipal UserDto currentUser) {

        User user = userDtoUserTypeMap.map(currentUser);

        try {
            driverService.changeDriverStatus(user, driverDto.getStatus());
        } catch (ServiceException ex) {
            if (ex.getError() == ServiceError.DRIVER_STATUS_CHANGE) {
                logger.warn(ex.getMessage());
            }
        }

        return REDIRECT_ACCOUNT_DASHBOARD;
    }
}
