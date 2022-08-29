package org.darius.configuration;

import lombok.NoArgsConstructor;
import org.darius.configuration.model.CustomUser;
import org.darius.entity.User;
import org.darius.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@NoArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userService.getUserByUsername(username);
        if(userOptional.isPresent()){
            return new CustomUser(userOptional.get());
        }
        return null;
    }
}
