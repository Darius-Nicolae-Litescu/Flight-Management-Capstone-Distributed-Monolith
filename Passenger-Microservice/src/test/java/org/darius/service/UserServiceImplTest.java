package org.darius.service;

import org.darius.RestClient;
import org.darius.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
    void shouldGetUserById() {
        User user = new User();
        user.setUserId(1L);
        user.setUsername("DARIUS");

        when(restClient.restExchangeBearer(any(), any(), any())).thenReturn(ResponseEntity.of(Optional.of(user)));

        Optional<User> userOptional = userService.getUserById(1L);

        assertTrue(userOptional.isPresent());

        verify(restClient, times(1)).restExchangeBearer(any(), any(), any());
    }
}