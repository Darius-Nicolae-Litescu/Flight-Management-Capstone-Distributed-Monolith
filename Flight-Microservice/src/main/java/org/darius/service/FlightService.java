package org.darius.service;
import org.darius.dto.request.insert.ScheduleInsertDTO;
import org.darius.dto.request.update.FlightUpdateDTO;
import org.darius.dto.request.insert.FlightInsertDTO;
import org.darius.dto.response.FlightResponseDTO;
import org.darius.dto.response.ScheduleResponseDTO;
import org.darius.entity.flight.Flight;
import org.darius.exception.EntityNotFoundException;
import org.darius.wrapper.FlightOperationWrapper;

import java.util.List;
import java.util.Optional;

public interface FlightService {

	FlightOperationWrapper<FlightResponseDTO> addFlight(FlightInsertDTO flightInsertDTO) throws EntityNotFoundException;

	FlightOperationWrapper<FlightResponseDTO> updateFlight(FlightUpdateDTO flightUpdateDTO) throws EntityNotFoundException;

	FlightOperationWrapper<FlightResponseDTO> deleteFlight(Long id) throws EntityNotFoundException;
	ScheduleResponseDTO addSchedule(Long flightId, ScheduleInsertDTO scheduleInsertDTO) throws EntityNotFoundException;

	List<FlightResponseDTO> getFlightsByFlightName(String flightName);

	List<FlightResponseDTO> getFlightsByDepartureCity(String city1);

	List<FlightResponseDTO> getFlightsByArrivalCity(String city2);

	List<FlightResponseDTO> getFlightsByFlightType(String flightType);

	List<Flight> getAllFlight();

	List<Long> getAllFlightIds();

	FlightResponseDTO getFlightById(Long flightId) throws EntityNotFoundException;

	Flight getFlightEntityById(Long flightId) throws EntityNotFoundException;

	Optional<Flight> getFlightModelById(Long flightId);

	Long getNewSeatId(Long flightId) throws EntityNotFoundException;

	Long countSeatsByFlightId(Long flightId);

    List<Flight> findFlightsByIds(List<Long> flightIds);

	Boolean checkIfFlightExists(Long flightId);
}
