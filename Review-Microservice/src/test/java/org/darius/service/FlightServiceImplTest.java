package org.darius.service;

import org.darius.RestClient;
import org.darius.dto.request.insert.CommentInsertDTO;
import org.darius.dto.response.CommentResponseDTO;
import org.darius.entity.Comment;
import org.darius.entity.Review;
import org.darius.exception.EntityNotFoundException;
import org.darius.mapper.CommentMapper;
import org.darius.repository.CommentRepository;
import org.darius.repository.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
    @DisplayName("The method should check if the flight exists")
    void checkIfFlightExistsShouldReturnTrue() {
        Long flightId = 1L;
        when(restClient.restExchangeBearer(anyString(), any(), any())).thenReturn(ResponseEntity.of(Optional.of(Boolean.TRUE)));
        Boolean exists = flightService.checkIfFlightExists(flightId);
        assertEquals(Boolean.TRUE, exists);

        verify(restClient).restExchangeBearer(anyString(), any(), any());
    }

}