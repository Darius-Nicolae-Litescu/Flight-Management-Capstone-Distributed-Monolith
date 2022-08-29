package org.darius.controller.user;

import org.darius.dto.response.FlightResponseDTO;
import org.darius.entity.flight.Flight;
import org.darius.exception.EntityNotFoundException;
import org.darius.service.FlightService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("/api/flight/user")
public class FlightUserOperationsController {
    private final  Logger logger = LoggerFactory.getLogger(FlightUserOperationsController.class);
    @Autowired
    private FlightService flightService;

    @GetMapping("/{flightId}")
    public FlightResponseDTO getFlightBasedOnId(@PathVariable Long flightId) throws EntityNotFoundException {
        logger.info("Trying to get flight with id: {}", flightId);
        FlightResponseDTO flightResponseDto = flightService.getFlightById(flightId);
        return flightResponseDto;
    }

    @PostMapping("/seat/new/{flightId}")
    public Long getNewSeatId(@PathVariable Long flightId) throws EntityNotFoundException {
        logger.info("Trying to get new seat id for flight with id: {}", flightId);
        Long seatId = flightService.getNewSeatId(flightId);
        return seatId;
    }

    @GetMapping("/all/ids")
    public List<Long> getAllFlights() {
        logger.info("Trying to get all flights");
        return flightService.getAllFlightIds();
    }

    @GetMapping("/entity/{flightId}")
    public ResponseEntity<Flight> getFlightEntity(@PathVariable Long flightId) throws EntityNotFoundException {
        logger.info("Trying to get flight entity with id: {}", flightId);
        Flight flight = flightService.getFlightEntityById(flightId);
        return new ResponseEntity<>(flight, HttpStatus.OK);
    }

    @GetMapping("/seat/count/{flightId}")
    public Long countSeatsByFlightId(@PathVariable Long flightId) throws EntityNotFoundException {
        logger.info("Trying to count seats for flight with id: {}", flightId);
        Long count = flightService.countSeatsByFlightId(flightId);
        return count;
    }

    @GetMapping("/all/{flightIds}")
    public List<Flight> findFlightsByBookingIds(@PathVariable List<Long> flightIds) throws EntityNotFoundException {
        logger.info("Trying to find flights by ids: {}", flightIds);
        List<Flight> flights = flightService.findFlightsByIds(flightIds);
        return flights;
    }

    @GetMapping("/check/{flightId}")
    public ResponseEntity<Boolean> checkIfFlightExists(@PathVariable Long flightId) throws EntityNotFoundException {
        logger.info("Trying to check if flight with id: {} exists", flightId);
        Boolean exists = flightService.checkIfFlightExists(flightId);
        return new ResponseEntity<>(exists, HttpStatus.OK);
    }

}