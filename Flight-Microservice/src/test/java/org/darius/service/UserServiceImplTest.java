package org.darius.service;

import org.darius.RestClient;
import org.darius.model.User;
import org.darius.service.builder.UserBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class UserServiceImplTest {


    @Mock
    private RestClient restClient;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    @DisplayName("Should get all users")
    void shouldGetAllUsers() {
        User user = UserBuilder.random().build();
        User user2 = UserBuilder.random().build();

        List<User> users = new ArrayList<>();
        users.add(user);
        users.add(user2);

        when(restClient.restExchangeBearer(anyString(), any(), any())).thenReturn(ResponseEntity.of(Optional.of(users)));
        List<User> usersFromService = userService.getAllUsers();
        assertEquals(2, usersFromService.size());
    }
}