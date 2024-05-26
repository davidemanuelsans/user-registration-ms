package com.user_registration.user_registration_ms.services;

import com.user_registration.user_registration_ms.config.exceptions.GenericException;
import com.user_registration.user_registration_ms.dtos.UpdateUserRequestDto;
import com.user_registration.user_registration_ms.dtos.UpdateUserResponseDto;
import com.user_registration.user_registration_ms.entities.User;
import com.user_registration.user_registration_ms.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService {

    Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    public UpdateUserResponseDto updateUser(UpdateUserRequestDto updateRequest, String email) throws GenericException {
        try {
            User user = userRepository.findByEmail(email).orElseThrow();
            user.setName(updateRequest.getName());
            user.setModified(LocalDateTime.now());
            userRepository.save(user);

            return UpdateUserResponseDto
                    .builder()
                    .id(user.getId())
                    .name(user.getName())
                    .email(user.getEmail())
                    .build();
        } catch (Exception e) {
            logger.error("[update] Error when updating: {}", e.getMessage());
            throw new GenericException("Unable to update user");
        }
    }
}
