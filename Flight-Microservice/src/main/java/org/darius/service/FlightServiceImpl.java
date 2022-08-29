package org.darius.service;

import org.darius.dto.request.insert.ScheduleInsertDTO;
import org.darius.dto.request.update.FlightUpdateDTO;
import org.darius.dto.request.insert.FlightInsertDTO;
import org.darius.dto.response.FlightResponseDTO;
import org.darius.dto.response.ScheduleResponseDTO;
import org.darius.entity.flight.Flight;
import org.darius.entity.flight.Schedule;
import org.darius.entity.flight.Seat;
import org.darius.entity.location.City;
import org.darius.exception.EntityNotFoundException;
import org.darius.mapper.FlightMapper;
import org.darius.repository.CityRepository;
import org.darius.repository.FlightRepository;
import org.darius.repository.SeatRepository;
import org.darius.wrapper.FlightOperationWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FlightServiceImpl implements FlightService {
	Logger logger = LoggerFactory.getLogger(FlightServiceImpl.class);


	private final FlightRepository flightRepository;

	private final CityRepository cityRepository;

	private final SeatRepository seatRepository;
	
	@Autowired
	public FlightServiceImpl(FlightRepository flightRepository, CityRepository cityRepository, SeatRepository seatRepository) {
		this.flightRepository = flightRepository;
		this.cityRepository = cityRepository;
		this.seatRepository = seatRepository;
	}

	@Override
	@Transactional
	public FlightOperationWrapper<FlightResponseDTO> addFlight(FlightInsertDTO flightInsertDTO) throws EntityNotFoundException {
		logger.info("Trying to add flight: {}", flightInsertDTO.getFlightName());
		Flight flight = FlightMapper.flightInsertDTOToFlight(flightInsertDTO);
		Optional<City> departureCity = cityRepository.findById(flightInsertDTO.getDepartureCityId());
		Optional<City> arrivalCity = cityRepository.findById(flightInsertDTO.getArrivalCityId());
		if (!departureCity.isPresent()) {
			throw new EntityNotFoundException(flightInsertDTO.getDepartureCityId());
		}
		if (!arrivalCity.isPresent()) {
			throw new EntityNotFoundException(flightInsertDTO.getArrivalCityId());
		}
		Flight savedFlight = flightRepository.save(flight);

		arrivalCity.get().getArrivalCities().add(flight);
		departureCity.get().getDepartureCities().add(flight);

		cityRepository.save(arrivalCity.get());
		cityRepository.save(departureCity.get());

		return new FlightOperationWrapper<>("Flight has been inserted",
				FlightMapper.flightToFlightResponseDTO(savedFlight));
	}

	@Override
	@Transactional
	public FlightOperationWrapper<FlightResponseDTO> updateFlight(FlightUpdateDTO flightUpdateDTO) throws EntityNotFoundException {
		logger.info("Trying to update flight: {}", flightUpdateDTO.getFlightName());
		Optional<Flight> flightFromDatabase = flightRepository.findById(flightUpdateDTO.getId());
		Optional<City> departureCity = cityRepository.findById(flightUpdateDTO.getDepartureCityId());
		Optional<City> arrivalCity = cityRepository.findById(flightUpdateDTO.getArrivalCityId());
		if (flightFromDatabase.isPresent()) {
			Flight flight = flightFromDatabase.get();
			flight.setFlightName(flightUpdateDTO.getFlightName());
			flight.setFlightType(flightUpdateDTO.getFlightType());
			flightRepository.save(flight);
			if (!departureCity.isPresent()) {
				throw new EntityNotFoundException(flightUpdateDTO.getDepartureCityId());
			}
			if (!arrivalCity.isPresent()) {
				throw new EntityNotFoundException(flightUpdateDTO.getArrivalCityId());
			}

			arrivalCity.get().getArrivalCities().add(flight);
			departureCity.get().getDepartureCities().add(flight);
			cityRepository.save(arrivalCity.get());
			cityRepository.save(departureCity.get());
			return new FlightOperationWrapper<>("Flight has been updated",
					FlightMapper.flightToFlightResponseDTO(flightFromDatabase.get()));
		} else {
			throw new EntityNotFoundException(flightUpdateDTO.getId());
		}
	}

	@Override
	public FlightOperationWrapper<FlightResponseDTO> deleteFlight(Long id) throws EntityNotFoundException {
		logger.info("Trying to delete flight with id: {}", id);
		Optional<Flight> flight = flightRepository.findById(id);
		if (!flight.isPresent()) {
			throw new EntityNotFoundException(id);
		}
		flightRepository.delete(flight.get());
		return new FlightOperationWrapper<>("Flight has been deleted",
				FlightMapper.flightToFlightResponseDTO(flight.get()));
	}

	@Override
	public ScheduleResponseDTO addSchedule(Long flightId, ScheduleInsertDTO scheduleInsertDTO) throws EntityNotFoundException {
		logger.info("Trying to add schedule for flight with id: {}", flightId);
		Optional<Flight> flight = flightRepository.findById(flightId);
		if (!flight.isPresent()) {
			throw new EntityNotFoundException(flightId);
		}
		Schedule schedule = FlightMapper.scheduleInsertDTOToSchedule(scheduleInsertDTO);

		flight.get().setFlightSchedule(schedule);
		flightRepository.save(flight.get());

		ScheduleResponseDTO scheduleResponseDTO = FlightMapper.scheduleToScheduleResponseDTO(schedule, flightId);
		scheduleResponseDTO.setScheduleId(flight.get().getFlightSchedule().getScheduleId());
		return scheduleResponseDTO;
	}

	@Override
	public List<FlightResponseDTO> getFlightsByFlightName(String flightName) {
		logger.info("Trying to get flights with name: {}", flightName);
		List<Flight> flightList = flightRepository.findByFlightName(flightName);
		List<FlightResponseDTO> flightResponseDTOS = flightList.stream()
				.map(flight -> FlightMapper.flightToFlightResponseDTO(flight))
				.collect(Collectors.toList());
		return flightResponseDTOS;
	}

	@Override
	public List<FlightResponseDTO> getFlightsByDepartureCity(String departureCity) {
		logger.info("Trying to get flights with departure city: {}", departureCity);
		List<Flight> flightList = cityRepository.findByDepartureCityLike(departureCity);
		List<FlightResponseDTO> flightResponseDTOS = flightList.stream()
				.map(flight -> FlightMapper.flightToFlightResponseDTO(flight))
				.collect(Collectors.toList());
		return flightResponseDTOS;
	}

	@Override
	public List<FlightResponseDTO> getFlightsByArrivalCity(String arrivalCity) {
		logger.info("Trying to get flights with arrival city: {}", arrivalCity);
		List<Flight> flightList = cityRepository.findByArrivalCityLike(arrivalCity);
		List<FlightResponseDTO> flightResponseDTOS = flightList.stream()
				.map(flight -> FlightMapper.flightToFlightResponseDTO(flight))
				.collect(Collectors.toList());
		return flightResponseDTOS;
	}

	@Override
	public List<FlightResponseDTO> getFlightsByFlightType(String flightType) {
		logger.info("Trying to get flights with flight type: {}", flightType);
		List<Flight> flightList = flightRepository.findByFlightType(flightType);
		List<FlightResponseDTO> flightResponseDTOS = flightList.stream()
				.map(flight -> FlightMapper.flightToFlightResponseDTO(flight))
				.collect(Collectors.toList());
		return flightResponseDTOS;
	}

	@Override
	public List<Flight> getAllFlight() {
		logger.info("Trying to get all flights");
		return flightRepository.findAll();
	}

	@Override
	public List<Long> getAllFlightIds() {
		logger.info("Trying to get all flights ids");
		return flightRepository.findAllIds();
	}


	@Override
	public Optional<Flight> getFlightModelById(Long flightId) {
		logger.info("Trying to get flight with id: {}", flightId);
		return flightRepository.findById(flightId);
	}

	@Override
	@Transactional
	public Long getNewSeatId(Long flightId) throws EntityNotFoundException {
		logger.info("Trying to get new seat id for flight with id: {}", flightId);
		Optional<Flight> flight = flightRepository.findById(flightId);
		if (!flight.isPresent()) {
			throw new EntityNotFoundException(flightId);
		}
		Seat seat = new Seat();
		seat = seatRepository.save(seat);
		flight.get().getSeats().add(seat);
		flightRepository.save(flight.get());
		return seat.getSeatId();
	}

	@Override
	public Long countSeatsByFlightId(Long flightId) {
		logger.info("Trying to count seats for flight with id: {}", flightId);
		return flightRepository.countSeatsByFlightId(flightId);
	}

	@Override
	public List<Flight> findFlightsByIds(List<Long> flightIds) {
		logger.info("Trying to find flights with ids: {}", flightIds);
		return flightRepository.findAllById(flightIds);
	}

	@Override
	public Boolean checkIfFlightExists(Long flightId) {
		logger.info("Trying to check if flight with id: {} exists", flightId);
		return flightRepository.existsById(flightId);
	}

	@Override
	public FlightResponseDTO getFlightById(Long flightId) throws EntityNotFoundException {
		logger.info("Trying to get flight with id: {}", flightId);
		Optional<Flight> flight = flightRepository.findById(flightId);
		if (!flight.isPresent()) {
			throw new EntityNotFoundException(flightId);
		}
		FlightResponseDTO flightResponseDTO = new FlightResponseDTO();
		flightResponseDTO.setFlightNumber(flightId);
		flightResponseDTO.setFlightName(flight.get().getFlightName());
		return flightResponseDTO;
	}

	@Override
	public Flight getFlightEntityById(Long flightId) throws EntityNotFoundException {
		logger.info("Trying to get flight entity with id: {}", flightId);
		Optional<Flight> flight = flightRepository.findById(flightId);
		if (!flight.isPresent()) {
			throw new EntityNotFoundException(flightId);
		}
		return flight.get();
	}

}
