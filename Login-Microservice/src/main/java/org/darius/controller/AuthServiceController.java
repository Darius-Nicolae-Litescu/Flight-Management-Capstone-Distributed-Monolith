package org.darius.controller;

import org.darius.entity.User;
import org.darius.exception.EntityNotFoundException;
import org.darius.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user/auth-service/")
public class AuthServiceController {
    private final Logger logger = LoggerFactory.getLogger(AuthServiceController.class);

    @Autowired
    private UserService userService;

    @GetMapping("/{username}")
    public ResponseEntity<User> getUserDetailsByUsername(@PathVariable String username) throws EntityNotFoundException {
        logger.info("Trying to get user with username: {}", username);
        Optional<User> userDetailsOptional = userService.getUserByUsername(username);
        if (!userDetailsOptional.isPresent()) {
            throw new EntityNotFoundException("User not found", 0L);
        }
        User user = userDetailsOptional.get();
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @GetMapping("/entity/id/{userId}")
    public ResponseEntity<User> getUserDetailsById(@PathVariable Long userId) throws EntityNotFoundException {
        logger.info("Trying to get user with id: {}", userId);
        Optional<User> userDetailsOptional = userService.getUserEntityById(userId);
        if (!userDetailsOptional.isPresent()) {
            throw new EntityNotFoundException(userId);
        }
        User user = userDetailsOptional.get();
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        logger.info("Trying to get all users");
        List<User> users = userService.getAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/users/ids")
    public ResponseEntity<List<Long>> getAllUsersIds() {
        logger.info("Trying to get all users ids");
        List<Long> usersIds = userService.getAllIds();
        return new ResponseEntity<>(usersIds, HttpStatus.OK);
    }

    private String getUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}