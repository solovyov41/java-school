package com.tlg.web.controller.account;

import com.tlg.core.dto.UserDto;
import com.tlg.core.dto.UserViewDto;
import com.tlg.core.entity.User;
import com.tlg.core.service.UserService;
import com.tlg.core.service.exceptions.ServiceException;
import com.tlg.core.validation.groups.RoleChangeChecks;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("account/users")
public class UsersController {

    @Autowired
    UserService userService;

    @Autowired
    private TypeMap<UserViewDto, User> userViewUserTypeMap;

    @GetMapping
    public String getDashboard(Model model, @AuthenticationPrincipal UserDto customUser) throws ServiceException {
        List<User> users = userService.findAll();
        users.removeIf(user -> user.getEmail().equals(customUser.getEmail()));

        model.addAttribute("users", users);
        return "/account/users";
    }

    @GetMapping("/edit")
    public String getUserEditPage(@RequestParam(name = "email") String email, Model model) throws ServiceException {
        User user = userService.findByIdentifier(email);
        model.addAttribute("user", user);
        return "/account/userEdit";
    }

    @PostMapping("/edit")
    public String updateUser(@ModelAttribute("user") @Validated(RoleChangeChecks.class) UserViewDto userViewDto,
                             BindingResult bindingResult) throws ServiceException {

        if (bindingResult.hasErrors()) {
            return "/account/profile";
        }

        User user = userViewUserTypeMap.map(userViewDto);
        userService.update(user.getEmail(), user, true);
        return "redirect:/account/users";
    }

    @GetMapping("/delete")
    public String deleteUser(@RequestParam(name = "email") String email) throws ServiceException {
        userService.delete(email);
        return "redirect:/account/users";
    }


}
