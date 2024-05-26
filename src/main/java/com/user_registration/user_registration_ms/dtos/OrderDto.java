package com.user_registration.user_registration_ms.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class OrderDto {
    private Long id;
    private float amount;
    private LocalDateTime purchaseDate;
}
