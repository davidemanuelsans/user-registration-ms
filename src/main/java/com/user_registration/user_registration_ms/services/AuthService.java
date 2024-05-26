package com.user_registration.user_registration_ms.services;

import com.user_registration.user_registration_ms.config.exceptions.GenericException;
import com.user_registration.user_registration_ms.config.JWTUtils;
import com.user_registration.user_registration_ms.config.exceptions.UnauthorizedException;
import com.user_registration.user_registration_ms.dtos.CreateUserRequestDto;
import com.user_registration.user_registration_ms.dtos.UserLoginDto;
import com.user_registration.user_registration_ms.dtos.UserLoginResponse;
import com.user_registration.user_registration_ms.dtos.UserResponseDto;
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

    public UserResponseDto createUser(CreateUserRequestDto requestDto) throws GenericException {
        String email = requestDto.getEmail();

        if (userRepository.existsByEmail(email)) {
            throw new GenericException("Email already exists");
        }

        User userEntity = new User();
        userEntity.setEmail(email);
        userEntity.setPassword(encoder.encode(requestDto.getPassword()));
        userEntity.setName(requestDto.getName());
        userEntity.setRole(Role.USER);
        userEntity = userRepository.save(userEntity);

        return new UserResponseDto(userEntity.getId(),
                email,
                userEntity.getName()
        );
    }

    public UserLoginResponse login(UserLoginDto request) throws UnauthorizedException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            User user = userRepository.findByEmail(request.getEmail()).orElseThrow();
            String token = jwtUtils.generateToken(user);

            UserLoginResponse response = new UserLoginResponse();
            response.setId(user.getId());
            response.setToken(token);
            return response;
        } catch (Exception e) {
            logger.error("[login] Error when logging in: {}", e.getMessage());
            // Details omitted in order to ofuscate unathentication details
            throw new UnauthorizedException("Unable to login");
        }
    }
}
