package com.user_registration.user_registration_ms.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class PhoneRequestDto {
    private String number;
    @Max(3)
    @Min(1)
    private String citycode;
    @Max(3)
    @Min(1)
    private String countrycode;
}
