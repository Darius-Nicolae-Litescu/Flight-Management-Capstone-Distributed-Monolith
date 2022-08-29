package org.darius.service;

import org.darius.RestClient;
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

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class FlightServiceImplTest {

    @Mock
    private RestClient restClient;

    @InjectMocks
    private FlightServiceImpl flightService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("getFlightById should get flight by id")
    void shouldGetFlightById() {
        Flight flight = FlightBuilder.random().build();
        when(restClient.restExchangeBearer(anyString(), any(), any())).thenReturn(ResponseEntity.ok(flight));

        Optional<Flight> flightOptional = flightService.getFlightById(1L);

        assertTrue(flightOptional.isPresent());
        assertEquals(flightOptional.get(), flight);

        verify(restClient, times(1)).restExchangeBearer(anyString(), any(), any());
    }


    @Test
    @DisplayName("getNewSeatId should get new seat id")
    void shouldGetNewSeatId() {
        Long seatId = 1L;

        when(restClient.restExchangeBearer(anyString(), any(), any())).thenReturn(ResponseEntity.ok(seatId));

        Long seatIdFromResponse = flightService.getNewSeatId(1L);

        assertEquals(seatId, seatIdFromResponse);

        verify(restClient, times(1)).restExchangeBearer(anyString(), any(), any());
    }

    @Test
    @DisplayName("shouldGetAllFlightIds should get all flight ids")
    void shouldGetAllFlightIds() {
        List<Long> flightIds = new ArrayList<>();
        flightIds.add(1L);
        flightIds.add(2L);
        flightIds.add(3L);

        when(restClient.restExchangeBearer(any(), any(), any())).thenReturn(ResponseEntity.ok(flightIds));

        List<Long> flightIdsFromResponse = flightService.getAllFlightIds();

        assertEquals(flightIds, flightIdsFromResponse);

        verify(restClient, times(1)).restExchangeBearer(anyString(), any(), any());
    }

    @Test
    @DisplayName("shouldCountSeatsByFlightId should get all flights")
    void shouldCountSeatsByFlightId() {
        Long seatCount = 1L;

        when(restClient.restExchangeBearer(anyString(), any(), any())).thenReturn(ResponseEntity.ok(seatCount));

        Long seatCountFromResponse = flightService.countSeatsByFlightId(1L);

        assertEquals(seatCount, seatCountFromResponse);

        verify(restClient, times(1)).restExchangeBearer(anyString(), any(), any());
    }

    @Test
    @DisplayName("shouldFindFlightsByIds should get all flights")
    void shouldFindFlightsByIds() {
        List<Long> flightIds = new ArrayList<>();
        flightIds.add(1L);
        flightIds.add(2L);
        flightIds.add(3L);
        List<Flight> flights = new ArrayList<>();
        flights.add(FlightBuilder.random().build());
        flights.add(FlightBuilder.random().build());
        flights.add(FlightBuilder.random().build());
        when(restClient.restExchangeBearer(anyString(), any(), any())).thenReturn(ResponseEntity.ok(flights));

        List<Flight> flightsFromResponse = flightService.findFlightsByIds(flightIds);

        assertEquals(flights, flightsFromResponse);

        verify(restClient, times(1)).restExchangeBearer(anyString(), any(), any());
    }
}