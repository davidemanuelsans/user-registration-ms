package com.user_registration.user_registration_ms.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserLoginResponse {
    private long id;
    private LocalDateTime created;
    private LocalDateTime modified;
    @JsonProperty("last_login")
    private LocalDateTime lastLogin;
    private String token;
    @JsonProperty("isactive")
    private boolean isActive;
}
