package com.user_registration.user_registration_ms.controllers;

import com.user_registration.user_registration_ms.config.exceptions.GenericException;
import com.user_registration.user_registration_ms.config.exceptions.UnauthorizedException;
import com.user_registration.user_registration_ms.dtos.CreateUserRequestDto;
import com.user_registration.user_registration_ms.dtos.UserLoginDto;
import com.user_registration.user_registration_ms.dtos.UserLoginResponse;
import com.user_registration.user_registration_ms.dtos.UserResponseDto;
import com.user_registration.user_registration_ms.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public UserResponseDto register(@Valid @RequestBody CreateUserRequestDto requestDto)
            throws GenericException {
        return authService.createUser(requestDto);
    }

    @PostMapping("/login")
    public UserLoginResponse register(@RequestBody UserLoginDto requestDto)
            throws UnauthorizedException {
        return authService.login(requestDto);
    }
}
