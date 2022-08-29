package org.darius.service;

import org.darius.RestClient;
import org.junit.jupiter.api.*;

import org.darius.dto.request.insert.AdminBookingInsertDTO;
import org.darius.dto.response.AdminBookingResponseDTO;
import org.darius.dto.response.UserDetailsResponseDTO;
import org.darius.entity.Booking;
import org.darius.exception.EntityNotFoundException;
import org.darius.exception.SeatCapacityExceededException;
import org.darius.model.Flight;
import org.darius.model.Passenger;
import org.darius.model.User;
import org.darius.repository.BookingRepository;
import org.darius.service.builder.BookingBuilder;
import org.darius.service.builder.FlightBuilder;
import org.darius.service.builder.UserBuilder;
import org.darius.wrapper.ListWrapper;
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

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class UserPassengerServiceImplTest {

    @Mock
    private RestClient restClient;

    @InjectMocks
    private UserPassengerServiceImpl userPassengerService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void shouldGetUserById() {
        User user = UserBuilder.random().build();
        Passenger passenger = new Passenger();

        when(restClient.restExchangeBearer(any(), any(), any())).thenReturn(ResponseEntity.ok(user)).thenReturn(ResponseEntity.ok(passenger));

        Optional<User> userOptional = userPassengerService.getUserById(user.getUserId());

        Assertions.assertTrue(userOptional.isPresent());
        assertEquals(userOptional.get(), user);

        verify(restClient, times(2)).restExchangeBearer(any(), any(), any());
    }

    @Test
    void shouldGetAllUsers() {
        List<User> users = new ArrayList<>();
        users.add(UserBuilder.random().build());
        users.add(UserBuilder.random().build());
        users.add(UserBuilder.random().build());
        when(restClient.restExchangeBearer(anyString(), any(), any())).thenReturn(ResponseEntity.ok(users));

        List<User> userListResult = userPassengerService.getAllUsers();

        assertEquals(3, userListResult.size());

        verify(restClient, times(1)).restExchangeBearer(anyString(), any(), any());

    }

    @Test
    void shouldGetAllUserIds() {
        List<Long> userIds = new ArrayList<>();
        userIds.add(1L);
        userIds.add(2L);
        userIds.add(3L);
        when(restClient.restExchangeBearer(anyString(), any(), any())).thenReturn(ResponseEntity.ok(userIds));

        List<Long> userIdsResult = userPassengerService.getAllUserIds();

        assertEquals(3, userIdsResult.size());

        verify(restClient, times(1)).restExchangeBearer(anyString(), any(), any());
    }


}