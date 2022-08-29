package org.darius.service;

import org.darius.RestClient;
import org.darius.model.Passenger;
import org.darius.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserPassengerServiceImpl implements UserService {
    @Autowired
    private RestClient restClient;

    @Override
    public Optional<User> getUserById(Long id) {
        ResponseEntity<User> user = restClient.restExchangeBearer("http://LOGIN-MICROSERVICE/api/user/auth-service/entity/id/" + id, HttpMethod.GET, User.class);
        if (user.getBody() != null) {
            ResponseEntity<Passenger> passengerResponseEntity
                    = restClient.restExchangeBearer("http://PASSENGER-MICROSERVICE/api/passenger/admin/user/" + user.getBody().getUserId(), HttpMethod.GET, Passenger.class);
            user.getBody().setPassenger(passengerResponseEntity.getBody());
        }
        return Optional.ofNullable(user.getBody());
    }

    @Override
    public Optional<User> getUserByUsername(String username) throws UsernameNotFoundException {
        ResponseEntity<User> userResponseEntity = restClient.restExchangeBearer("http://LOGIN-MICROSERVICE/api/user/auth-service/" + username, HttpMethod.GET, User.class);
        if (userResponseEntity.getBody() == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        ResponseEntity<Passenger> passengerResponseEntity
                = restClient.restExchangeBearer("http://PASSENGER-MICROSERVICE/api/passenger/admin/user/" + userResponseEntity.getBody().getUserId(), HttpMethod.GET, Passenger.class);
        userResponseEntity.getBody().setPassenger(passengerResponseEntity.getBody());
        return Optional.ofNullable(userResponseEntity.getBody());
    }

    @Override
    public Optional<User> getUserAndPassengerDetailsByJWT(String username) throws UsernameNotFoundException {
        ResponseEntity<User> userResponseEntity = restClient.restExchangeBearer("http://LOGIN-MICROSERVICE/api/user/auth-service/" + username, HttpMethod.GET, User.class);
        if (userResponseEntity.getBody() == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        ResponseEntity<Passenger> passengerResponseEntity
                = restClient.restExchangeBearer("http://PASSENGER-MICROSERVICE/api/passenger/user/", HttpMethod.GET, Passenger.class);
        userResponseEntity.getBody().setPassenger(passengerResponseEntity.getBody());
        return Optional.ofNullable(userResponseEntity.getBody());
    }

    @Override
    public List<User> getAllUsers() {
        ResponseEntity<List> usersResponse = restClient.restExchangeBearer("http://LOGIN-MICROSERVICE/api/user/auth-service/all", HttpMethod.GET, List.class);
        List<User> userList = usersResponse.getBody();
        return userList;
    }

    @Override
    public List<Long> getAllUserIds() {
        ResponseEntity<List> usersResponse = restClient.restExchangeBearer("http://LOGIN-MICROSERVICE/api/user/auth-service/all/ids", HttpMethod.GET, List.class);
        List<Long> userIds = usersResponse.getBody();
        return userIds;
    }
}
