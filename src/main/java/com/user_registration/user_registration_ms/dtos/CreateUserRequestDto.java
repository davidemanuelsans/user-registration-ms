package com.user_registration.user_registration_ms.dtos;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class CreateUserRequestDto {
    @Email(message = "Must be a valid email")
    private String email;
    private String password;
    private String name;
}
