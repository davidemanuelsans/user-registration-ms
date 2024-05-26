package com.user_registration.user_registration_ms.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateUserRequestDto {
    @NotBlank
    private String name;
}
