package com.tlg.web.controller.account;

import com.tlg.core.dto.DriverDto;
import com.tlg.core.dto.UserDto;
import com.tlg.core.dto.WaypointDto;
import com.tlg.core.entity.User;
import com.tlg.core.entity.enums.DriverStatus;
import com.tlg.core.entity.enums.Role;
import com.tlg.core.service.CarriageService;
import com.tlg.core.service.DriverService;
import com.tlg.core.service.UserService;
import com.tlg.core.service.exceptions.ServiceException;
import org.modelmapper.TypeMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/account")
public class DashboardController {

    private static final Logger logger = LoggerFactory.getLogger(DashboardController.class);

    @Autowired
    DriverService driverService;

    @Autowired
    UserService userService;

    @Autowired
    CarriageService carriageService;

    @Autowired
    private TypeMap<UserDto, User> userDtoUserTypeMap;

    @GetMapping
    public String getAccount() {
        return "redirect:/account/dashboard";
    }

    @GetMapping("/dashboard")
    public String getDashboard(@AuthenticationPrincipal UserDto currentUser, Model model) {
        try {
            switch (currentUser.getRole()) {
                case ADMIN:
                    List<User> registeredUsers = userService.findAllWithRole(Role.REGISTERED);
                    model.addAttribute("users", registeredUsers);
                    break;
                case DRIVER:
                    User user = userDtoUserTypeMap.map(currentUser);
                    DriverDto driverDto = driverService.findByUser(user);

                    if (driverDto.getCarriage() != null) {
                        driverDto.setCarriage(carriageService.findByIdentifier(driverDto.getCarriage().getUniqueNumber()));

                        List<WaypointDto> waypointDtos = driverDto.getCarriage().getWaypoints();
                        int firstUnvisitedWp = 0;
                        for (int i = 0, l = waypointDtos.size(); i < l; i++) {
                            if (!waypointDtos.get(i).isVisited()) {
                                firstUnvisitedWp = i;
                                break;
                            }
                        }
                        model.addAttribute("firstUnvisited", firstUnvisitedWp);

                        boolean allInShift = true;
                        for (DriverDto driver : driverDto.getCarriage().getDrivers()) {
                            if (driver.getStatus() == DriverStatus.REST) {
                                allInShift = false;
                            }
                        }
                        model.addAttribute("allInShift", allInShift);
                    }
                    model.addAttribute("driver", driverDto);
                    break;
                case DISPATCHER:
                    model.addAttribute("notAssignOrderList", carriageService.findNotAssigned());
                    model.addAttribute("inWorkOrderList", carriageService.findInProcess());
                    break;
                default:
                    break;
            }

        } catch (ServiceException ex) {
            logger.warn(ex.getError().getMessage());
        }

        return "account/dashboard";
    }
}
