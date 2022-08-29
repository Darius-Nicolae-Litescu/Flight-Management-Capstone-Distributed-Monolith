package org.darius.service;

import org.darius.RestClient;
import org.darius.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private RestClient restClient;

    @Override
    public Optional<User> getUserById(Long id) {
        ResponseEntity<User> user = restClient.restExchangeBearer("http://LOGIN-MICROSERVICE/api/user/auth-service/entity/id/" + id, HttpMethod.GET, User.class);
        return Optional.ofNullable(user.getBody());
    }

    @Override
    public Optional<User> getUserByUsername(String username) throws UsernameNotFoundException {
        ResponseEntity<User> userResponseEntity = restClient.restExchangeBearer("http://LOGIN-MICROSERVICE/api/user/auth-service/" + username, HttpMethod.GET, User.class);
        if (userResponseEntity.getBody() == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return Optional.ofNullable(userResponseEntity.getBody());
    }
}
