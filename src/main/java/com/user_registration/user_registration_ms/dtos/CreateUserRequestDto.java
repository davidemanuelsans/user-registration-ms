package com.user_registration.user_registration_ms.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.List;

@Data
public class CreateUserRequestDto {
    private static final String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{6,20}$";
    private static final String PASSWORD_PATTERN_FAILED_MESSAGE = "The password must contain uppercase, lowercase " +
            "characters and numbers. It also must be between 6 and 20 characters long";

    @NotBlank(message = "Name cannot be blank")
    private String name;

    @Email(message = "Must be a valid email")
    @NotBlank(message = "Email cannot be blank")
    private String email;

    @NotBlank(message = "Password cannot be blank")
    @Pattern(regexp = PASSWORD_PATTERN, flags = Pattern.Flag.MULTILINE, message = PASSWORD_PATTERN_FAILED_MESSAGE)
    private String password;

    private List<PhoneRequestDto> phones;
}
