package com.user_registration.user_registration_ms.controllers;

import com.user_registration.user_registration_ms.config.exceptions.NotFoundException;
import com.user_registration.user_registration_ms.dtos.OrdersResponse;
import com.user_registration.user_registration_ms.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public OrdersResponse getMyOrders(Principal principal)
            throws NotFoundException {
        return orderService.getMyOrders(principal.getName());
    }
}
