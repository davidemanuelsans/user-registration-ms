package com.user_registration.user_registration_ms.dtos;

import lombok.Data;

@Data
public class PhoneRequestDto {
    private String number;
    private String citycode;
    private String countrycode;
}
