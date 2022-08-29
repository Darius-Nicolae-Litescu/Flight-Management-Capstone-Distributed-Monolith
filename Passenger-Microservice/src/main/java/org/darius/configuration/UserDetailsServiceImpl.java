package org.darius.configuration;

import lombok.NoArgsConstructor;
import org.darius.RestClient;
import org.darius.configuration.model.CustomUser;
import org.darius.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@NoArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private RestClient restClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ResponseEntity<User> userResponseEntity = restClient.restExchangeBearer("http://LOGIN-MICROSERVICE/api/user/auth-service/" + username, HttpMethod.GET, User.class);
        if (userResponseEntity.getBody() == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new CustomUser(userResponseEntity.getBody());
    }

}
