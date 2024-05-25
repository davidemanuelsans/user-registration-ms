package com.user_registration.user_registration_ms.dtos;

import lombok.Data;

@Data
public class CreateUserRequestDto {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
}
