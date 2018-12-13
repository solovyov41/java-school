package com.tlg.web.controller;

import com.tlg.core.entity.User;
import com.tlg.core.service.UserService;
import com.tlg.core.service.exceptions.ServiceException;
import com.tlg.core.validation.groups.OrderedRegistrationChecks;
import com.tlg.core.dto.UserViewDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/registration")
public class RegistrationController {
    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    public String registration(@ModelAttribute("user") @Validated(OrderedRegistrationChecks.class) UserViewDto userViewDto,
                               BindingResult bindingResult) throws ServiceException {

        if (bindingResult.hasErrors()) {
            return "login/registration";
        }
        User user = modelMapper.map(userViewDto, User.class);
        userService.create(user);
        return "redirect:/login";
    }

    @GetMapping
    public String registration(Model model) {
        UserViewDto userViewDto = new UserViewDto();
        model.addAttribute("user", userViewDto);
        return "login/registration";
    }
}