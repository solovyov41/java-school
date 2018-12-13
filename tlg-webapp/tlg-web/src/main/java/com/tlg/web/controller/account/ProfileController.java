package com.tlg.web.controller.account;

import com.tlg.core.dto.UserDto;
import com.tlg.core.dto.UserViewDto;
import com.tlg.core.entity.User;
import com.tlg.core.service.UserService;
import com.tlg.core.service.exceptions.ServiceException;
import com.tlg.core.validation.groups.OrderedEditChecks;
import org.modelmapper.TypeMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/account")
public class ProfileController {

    private static final Logger logger = LoggerFactory.getLogger(ProfileController.class);

    private static final String PROFILE_VIEW = "/account/profile";

    @Autowired
    UserService userService;
    @Autowired
    private TypeMap<UserViewDto, User> userViewUserTypeMap;
    @Autowired
    private TypeMap<UserDto, UserViewDto> userDtoUserViewTypeMap;

    @GetMapping("/profile")
    public String getProfile(Model model, @AuthenticationPrincipal UserDto customUser) {
        UserViewDto userViewDto = userDtoUserViewTypeMap.map(customUser);
        model.addAttribute("user", userViewDto);
        return PROFILE_VIEW;
    }

    @PostMapping("/profile")
    public String updateProfile(@ModelAttribute("user") @Validated(OrderedEditChecks.class) UserViewDto userViewDto,
                                BindingResult bindingResult,
                                @AuthenticationPrincipal UserDto customUser, Model model) throws ServiceException {
        if (bindingResult.hasErrors()) {
            return PROFILE_VIEW;
        }

        String email = customUser.getEmail();
        User user = userViewUserTypeMap.map(userViewDto);
        userService.update(email, user, false);
        return "redirect:/account/profile";
    }
}
