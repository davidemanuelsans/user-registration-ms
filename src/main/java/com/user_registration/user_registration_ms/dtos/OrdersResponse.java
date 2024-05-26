package com.user_registration.user_registration_ms.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class OrdersResponse {
    private List<OrderDto> orders;
}
