package com.user_registration.user_registration_ms.services;

import com.user_registration.user_registration_ms.config.exceptions.ConflictResourceException;
import com.user_registration.user_registration_ms.config.exceptions.GenericException;
import com.user_registration.user_registration_ms.config.JWTUtils;
import com.user_registration.user_registration_ms.config.exceptions.UnauthorizedException;
import com.user_registration.user_registration_ms.dtos.CreateUserRequestDto;
import com.user_registration.user_registration_ms.dtos.UpdateUserRequestDto;
import com.user_registration.user_registration_ms.dtos.UpdateUserResponseDto;
import com.user_registration.user_registration_ms.dtos.UserLoginDto;
import com.user_registration.user_registration_ms.dtos.UserLoginResponse;
import com.user_registration.user_registration_ms.entities.Role;
import com.user_registration.user_registration_ms.entities.User;
import com.user_registration.user_registration_ms.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthService {

    Logger logger = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTUtils jwtUtils;

    public UserLoginResponse createUser(CreateUserRequestDto requestDto) throws ConflictResourceException {
        String email = requestDto.getEmail();

        if (userRepository.existsByEmail(email)) {
            throw new ConflictResourceException("Email already exists");
        }
        LocalDateTime now = LocalDateTime.now();

        User userEntity = new User();
        userEntity.setEmail(email);
        userEntity.setPassword(encoder.encode(requestDto.getPassword()));
        userEntity.setName(requestDto.getName());
        userEntity.setRole(Role.USER);
        userEntity.setCreated(now);
        userEntity.setLastLogin(now);
        userEntity.setActive(true);

        String token = jwtUtils.generateToken(userEntity);
        userEntity.setToken(token);

        userEntity = userRepository.save(userEntity);

        return UserLoginResponse
                .builder()
                .id(userEntity.getId())
                .created(userEntity.getCreated())
                .modified(userEntity.getModified())
                .lastLogin(now)
                .token(token)
                .isActive(userEntity.isActive())
                .build();
    }

    public UserLoginResponse login(UserLoginDto request) throws UnauthorizedException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            User user = userRepository.findByEmail(request.getEmail()).orElseThrow();
            String token = jwtUtils.generateToken(user);
            LocalDateTime previousLoginDate = user.getLastLogin();

            // TODO: the instructions stated "El token deberá ser persistido junto con el usuario".
            // Is this really necessary?
            try {
                user.setToken(token);
                user.setLastLogin(LocalDateTime.now());
                userRepository.save(user);
            } catch (Exception e) {}

            return UserLoginResponse
                    .builder()
                    .id(user.getId())
                    .created(user.getCreated())
                    .modified(user.getModified())
                    .lastLogin(previousLoginDate)
                    .token(token)
                    .isActive(user.isActive())
                    .build();
        } catch (Exception e) {
            logger.error("[login] Error when logging in: {}", e.getMessage());
            // Details omitted in order to ofuscate unathentication details
            throw new UnauthorizedException("Unable to login");
        }
    }
}
