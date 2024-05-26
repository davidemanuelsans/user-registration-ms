package com.user_registration.user_registration_ms.services;

import com.user_registration.user_registration_ms.config.exceptions.NotFoundException;
import com.user_registration.user_registration_ms.dtos.OrderDto;
import com.user_registration.user_registration_ms.dtos.OrdersResponse;
import com.user_registration.user_registration_ms.entities.Order;
import com.user_registration.user_registration_ms.entities.User;
import com.user_registration.user_registration_ms.repositories.OrderRepository;
import com.user_registration.user_registration_ms.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class OrderServiceTest {
    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private OrderService orderService;

    private User user;
    private Order order1;
    private Order order2;

    @BeforeEach
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");

        order1 = new Order();
        order1.setId(1L);
        order1.setAmount(100);
        order1.setPurchaseDate(LocalDateTime.now());
        order1.setUserId(1L);

        order2 = new Order();
        order2.setId(2L);
        order2.setAmount(200);
        order2.setPurchaseDate(LocalDateTime.now());
        order2.setUserId(1L);
    }

    @Test
    void testGetMyOrdersSuccess() throws NotFoundException {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(orderRepository.findByUserId(anyLong())).thenReturn(Arrays.asList(order1, order2));

        OrdersResponse response = orderService.getMyOrders("test@example.com");

        assertNotNull(response);
        assertEquals(2, response.getOrders().size());
        List<Long> orderIds = response.getOrders().stream().map(OrderDto::getId).collect(Collectors.toList());
        assertTrue(orderIds.contains(order1.getId()));
        assertTrue(orderIds.contains(order2.getId()));

        verify(userRepository, times(1)).findByEmail(anyString());
        verify(orderRepository, times(1)).findByUserId(anyLong());
    }

    @Test
    void testGetMyOrdersUserNotFound() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            orderService.getMyOrders("test@example.com");
        });

        verify(userRepository, times(1)).findByEmail(anyString());
        verify(orderRepository, never()).findByUserId(anyLong());
    }

    @Test
    void testGetMyOrdersNoOrdersFound() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(orderRepository.findByUserId(anyLong())).thenReturn(Collections.emptyList());

        assertThrows(NotFoundException.class, () -> {
            orderService.getMyOrders("test@example.com");
        });

        verify(userRepository, times(1)).findByEmail(anyString());
        verify(orderRepository, times(1)).findByUserId(anyLong());
    }
}