package org.darius.service;

import org.darius.RestClient;
import org.darius.castutils.CastUtils;
import org.darius.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    private final  Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private RestClient restClient;

    @Override
    public List<User> getAllUsers(){
        logger.info("Trying to get all users");
        ResponseEntity<List> users = restClient.restExchangeBearer(
                "http://LOGIN-MICROSERVICE/api/user/user/users",
                HttpMethod.GET,
                List.class
        );
        if(users.getBody() != null){
            return new CastUtils<User>().castList(users.getBody(), User.class);
        }
        return null;
    }
}
