package com.user_registration.user_registration_ms.dtos;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UpdateUserResponseDto {
    private String name;
    private long id;
    private String email;
}
