package com.user_registration.user_registration_ms.services;

import com.user_registration.user_registration_ms.config.exceptions.GenericException;
import com.user_registration.user_registration_ms.dtos.UpdateUserRequestDto;
import com.user_registration.user_registration_ms.dtos.UpdateUserResponseDto;
import com.user_registration.user_registration_ms.entities.User;
import com.user_registration.user_registration_ms.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private UpdateUserRequestDto updateUserRequest;
    private User user;

    @BeforeEach
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeEach
    void setUp() {
        updateUserRequest = new UpdateUserRequestDto();
        updateUserRequest.setName("Updated Name");

        user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setName("Original Name");
        user.setModified(LocalDateTime.now());
    }

    @Test
    void testUpdateUserSuccess() throws GenericException {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        UpdateUserResponseDto response = userService.updateUser(updateUserRequest, "test@example.com");

        assertNotNull(response);
        assertEquals(user.getId(), response.getId());
        assertEquals(updateUserRequest.getName(), response.getName());
        assertEquals(user.getEmail(), response.getEmail());

        verify(userRepository, times(1)).findByEmail(anyString());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testUpdateUserUserNotFound() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        assertThrows(GenericException.class, () -> {
            userService.updateUser(updateUserRequest, "test@example.com");
        });

        verify(userRepository, times(1)).findByEmail(anyString());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testUpdateUserException() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        doThrow(new RuntimeException("Save failed")).when(userRepository).save(any(User.class));

        assertThrows(GenericException.class, () -> {
            userService.updateUser(updateUserRequest, "test@example.com");
        });

        verify(userRepository, times(1)).findByEmail(anyString());
        verify(userRepository, times(1)).save(any(User.class));
    }
}