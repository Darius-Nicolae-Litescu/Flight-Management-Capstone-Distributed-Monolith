package org.darius.controller.user;

import org.darius.dto.response.FlightResponseDTO;
import org.darius.exception.EntityNotFoundException;
import org.darius.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/flight/user")
public class FlightSearchController {
    private final FlightService flightService;

    @Autowired
    public FlightSearchController(FlightService flightService) {
        this.flightService = flightService;
    }

    @GetMapping("/flights/departureCity")
    public List<FlightResponseDTO> getFlightsByDepartureCity(@RequestParam(value = "departureCity") String departureCity) {
        List<FlightResponseDTO> flights = flightService.getFlightsByDepartureCity(departureCity);
        flights.forEach(flight -> {
            Link link = null;
            try {
                link = linkTo(methodOn(FlightUserOperationsController.class)
                        .getFlightBasedOnId(flight.getFlightNumber()))
                        .withSelfRel();
            } catch (EntityNotFoundException e) {
                throw new RuntimeException(e);
            }
            flight.add(link);
        });
        return flights;
    }

    @GetMapping("/flights/arrivalCity")
    public List<FlightResponseDTO> getFlightsByArrivalCity(@RequestParam(value = "arrivalCity") String arrivalCity) {
        List<FlightResponseDTO> flights = flightService.getFlightsByArrivalCity(arrivalCity);
        flights.forEach(flight -> {
            Link link = null;
            try {
                link = linkTo(methodOn(FlightUserOperationsController.class)
                        .getFlightBasedOnId(flight.getFlightNumber()))
                        .withSelfRel();
            } catch (EntityNotFoundException e) {
                throw new RuntimeException(e);
            }
            flight.add(link);
        });
        return flights;
    }

    @GetMapping("/flights/flightType")
    public List<FlightResponseDTO> getFlightsByFlightType(@RequestParam(value = "flightType") String flightType) {
        List<FlightResponseDTO> flights = flightService.getFlightsByFlightType(flightType);
        flights.forEach(flight -> {
            Link link = null;
            try {
                link = linkTo(methodOn(FlightUserOperationsController.class)
                        .getFlightBasedOnId(flight.getFlightNumber()))
                        .withSelfRel();
            } catch (EntityNotFoundException e) {
                throw new RuntimeException(e);
            }
            flight.add(link);
        });
        return flights;
    }

}
