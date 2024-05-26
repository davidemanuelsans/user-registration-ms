package com.user_registration.user_registration_ms.services;

import com.user_registration.user_registration_ms.config.exceptions.NotFoundException;
import com.user_registration.user_registration_ms.dtos.OrderDto;
import com.user_registration.user_registration_ms.dtos.OrdersResponse;
import com.user_registration.user_registration_ms.entities.Order;
import com.user_registration.user_registration_ms.entities.User;
import com.user_registration.user_registration_ms.repositories.OrderRepository;
import com.user_registration.user_registration_ms.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    public OrdersResponse getMyOrders(String email) throws NotFoundException {
        User user = userRepository
                .findByEmail(email)
                .orElseThrow( () -> new NotFoundException("No orders found") );
        List<Order> myOrders = orderRepository.findByUserId(user.getId());

        if (myOrders == null || myOrders.isEmpty()) throw new NotFoundException("No orders found");

        List<OrderDto> orders = myOrders
            .stream()
            .map( order -> new OrderDto(order.getId(), order.getAmount(), order.getPurchaseDate()) )
            .collect(Collectors.toList());

        return new OrdersResponse(orders);
    }
}
