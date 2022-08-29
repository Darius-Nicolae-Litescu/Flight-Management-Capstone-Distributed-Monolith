package org.darius.service;

import org.darius.model.Flight;

import java.util.List;
import java.util.Optional;

public interface FlightService {
    Optional<Flight> getFlightById(Long id);

    Long getNewSeatId(Long flightId);

    List<Long> getAllFlightIds();

    Long countSeatsByFlightId(Long flightId);

    List<Flight> findFlightsByIds(List<Long> bookingIds);
}
