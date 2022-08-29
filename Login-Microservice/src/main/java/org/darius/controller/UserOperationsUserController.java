package org.darius.controller;

import org.darius.configuration.jwt.JwtUtils;
import org.darius.dto.request.update.UserUpdateDTO;
import org.darius.dto.response.UserUpdateResponseDTO;
import org.darius.entity.User;
import org.darius.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user/user")
public class UserOperationsUserController {
    private final  Logger logger = LoggerFactory.getLogger(UserOperationsUserController.class);

    @Autowired
    private UserService userService;

    @PutMapping
    public ResponseEntity<UserUpdateResponseDTO> updateUser(@RequestBody UserUpdateDTO userUpdateDTO) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        logger.info("Trying to update user with username: {}", username);
        Optional<UserUpdateResponseDTO> userUpdateResponseDTOOptional = userService.updateUser(username, userUpdateDTO);
        if (!userUpdateResponseDTOOptional.isPresent()) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        UserUpdateResponseDTO userUpdated = userUpdateResponseDTOOptional.get();
        return new ResponseEntity<>(userUpdated, HttpStatus.ACCEPTED);
    }

    private String getUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}