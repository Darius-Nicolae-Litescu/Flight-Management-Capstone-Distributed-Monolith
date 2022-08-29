package org.darius.service;

import org.darius.model.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> getUserById(Long id);

    Optional<User> getUserByUsername(String username) throws UsernameNotFoundException;

    Optional<User> getUserAndPassengerDetailsByJWT(String username) throws UsernameNotFoundException;

    List<User> getAllUsers();

    List<Long> getAllUserIds();
}
