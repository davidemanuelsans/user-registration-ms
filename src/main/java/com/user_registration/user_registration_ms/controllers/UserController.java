package com.user_registration.user_registration_ms.controllers;

import com.user_registration.user_registration_ms.config.exceptions.GenericException;
import com.user_registration.user_registration_ms.dtos.UpdateUserRequestDto;
import com.user_registration.user_registration_ms.dtos.UpdateUserResponseDto;
import com.user_registration.user_registration_ms.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/update")
    public UpdateUserResponseDto update(@RequestBody UpdateUserRequestDto requestDto, Principal principal)
            throws GenericException {
        return userService.updateUser(requestDto, principal.getName());
    }
}
