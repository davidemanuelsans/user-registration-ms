package com.user_registration.user_registration_ms.services;

import com.user_registration.user_registration_ms.config.JWTUtils;
import com.user_registration.user_registration_ms.config.exceptions.ConflictResourceException;
import com.user_registration.user_registration_ms.config.exceptions.UnauthorizedException;
import com.user_registration.user_registration_ms.dtos.CreateUserRequestDto;
import com.user_registration.user_registration_ms.dtos.UserLoginDto;
import com.user_registration.user_registration_ms.dtos.UserLoginResponse;
import com.user_registration.user_registration_ms.entities.Role;
import com.user_registration.user_registration_ms.entities.User;
import com.user_registration.user_registration_ms.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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

class AuthServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder encoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JWTUtils jwtUtils;

    @InjectMocks
    private AuthService authService;

    private CreateUserRequestDto createUserRequestDto;
    private UserLoginDto userLoginDto;
    private User user;
    private String token;

    @BeforeEach
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeEach
    void setUp() {
        createUserRequestDto = new CreateUserRequestDto();
        createUserRequestDto.setEmail("test@example.com");
        createUserRequestDto.setPassword("password");
        createUserRequestDto.setName("Test User");

        userLoginDto = new UserLoginDto();
        userLoginDto.setEmail("test@example.com");
        userLoginDto.setPassword("password");

        user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setPassword("encodedPassword");
        user.setName("Test User");
        user.setRole(Role.USER);
        user.setCreated(LocalDateTime.now());
        user.setLastLogin(LocalDateTime.now());
        user.setActive(true);

        token = "testToken";
    }

    @Test
    void testCreateUserSuccess() throws ConflictResourceException {
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(encoder.encode(anyString())).thenReturn("encodedPassword");
        when(jwtUtils.generateToken(any(User.class))).thenReturn(token);
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserLoginResponse response = authService.createUser(createUserRequestDto);

        assertNotNull(response);
        assertEquals(user.getId(), response.getId());
        assertEquals(user.getCreated(), response.getCreated());
        assertEquals(token, response.getToken());
        assertTrue(response.isActive());

        verify(userRepository, times(1)).existsByEmail(anyString());
        verify(encoder, times(1)).encode(anyString());
        verify(jwtUtils, times(1)).generateToken(any(User.class));
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testCreateUserEmailExists() {
        when(userRepository.existsByEmail(anyString())).thenReturn(true);

        assertThrows(ConflictResourceException.class, () -> {
            authService.createUser(createUserRequestDto);
        });

        verify(userRepository, times(1)).existsByEmail(anyString());
        verify(encoder, never()).encode(anyString());
        verify(jwtUtils, never()).generateToken(any(User.class));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testLoginSuccess() throws UnauthorizedException {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(null);
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(jwtUtils.generateToken(any(User.class))).thenReturn(token);
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserLoginResponse response = authService.login(userLoginDto);

        assertNotNull(response);
        assertEquals(user.getId(), response.getId());
        assertEquals(user.getCreated(), response.getCreated());
        assertEquals(token, response.getToken());
        assertTrue(response.isActive());

        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userRepository, times(1)).findByEmail(anyString());
        verify(jwtUtils, times(1)).generateToken(any(User.class));
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testLoginUserNotFound() {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(null);
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        assertThrows(UnauthorizedException.class, () -> {
            authService.login(userLoginDto);
        });

        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userRepository, times(1)).findByEmail(anyString());
        verify(jwtUtils, never()).generateToken(any(User.class));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testLoginAuthenticationFailed() {
        doThrow(new RuntimeException("Authentication failed")).when(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));

        assertThrows(UnauthorizedException.class, () -> {
            authService.login(userLoginDto);
        });

        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userRepository, never()).findByEmail(anyString());
        verify(jwtUtils, never()).generateToken(any(User.class));
        verify(userRepository, never()).save(any(User.class));
    }
}