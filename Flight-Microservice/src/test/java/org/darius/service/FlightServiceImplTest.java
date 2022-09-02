package org.darius.service;

import org.darius.RestClient;
import org.darius.dto.request.insert.FlightInsertDTO;
import org.darius.dto.request.insert.ScheduleInsertDTO;
import org.darius.dto.request.update.FlightUpdateDTO;
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
import org.darius.repository.ScheduleRepository;
import org.darius.repository.SeatRepository;
import org.darius.service.builder.FlightBuilder;
import org.darius.wrapper.FlightOperationWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class FlightServiceImplTest {

    @Mock
    private RestClient restClient;

    @Mock
    private FlightRepository flightRepository;

    @Mock
    private CityRepository cityRepository;

    @Mock
    private SeatRepository seatRepository;

    @Mock
    private ScheduleRepository scheduleRepository;

    @InjectMocks
    private FlightServiceImpl flightService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Should test addFlight method of FlightServiceImpl")
    void shouldAddFlight() throws EntityNotFoundException {
        Flight flight = FlightBuilder.random().build();

        FlightInsertDTO flightInsertDTO = new FlightInsertDTO();
        flightInsertDTO.setFlightName(flight.getFlightName());
        flightInsertDTO.setDepartureCityId(1L);
        flightInsertDTO.setArrivalCityId(2L);
        flightInsertDTO.setFlightType(flight.getFlightType());
        flightInsertDTO.setSeatCapacity(flight.getFlightSeatCapacity());
        City arrivalCity = new City();
        arrivalCity.setId(2L);
        City departureCity = new City();
        departureCity.setId(1L);

        when(cityRepository.findById(1L)).thenReturn(Optional.of(departureCity));
        when(cityRepository.findById(2L)).thenReturn(Optional.of(arrivalCity));

        Flight resultingFlight = FlightMapper.flightInsertDTOToFlight(flightInsertDTO);
        when(flightRepository.save(any(Flight.class))).thenReturn(resultingFlight);

        FlightResponseDTO flightResponseDTO = FlightMapper.flightToFlightResponseDTO(resultingFlight);

        FlightOperationWrapper<FlightResponseDTO> flightOperationWrapper = flightService.addFlight(flightInsertDTO);
        assertEquals(flightOperationWrapper.getResult(), flightResponseDTO);

        verify(cityRepository).findById(1L);
        verify(cityRepository).findById(2L);

        verify(flightRepository).save(any(Flight.class));

    }

    @Test
    @DisplayName("Should test updateFlight method of FlightServiceImpl")
    void shouldUpdateFlight() throws EntityNotFoundException {
        Flight flight = FlightBuilder.random().build();

        FlightUpdateDTO flightUpdateDTO = new FlightUpdateDTO();
        flightUpdateDTO.setId(1L);
        flightUpdateDTO.setFlightName(flight.getFlightName());
        flightUpdateDTO.setDepartureCityId(1L);
        flightUpdateDTO.setArrivalCityId(2L);
        flightUpdateDTO.setFlightType(flight.getFlightType());
        City arrivalCity = new City();
        arrivalCity.setId(2L);
        City departureCity = new City();
        departureCity.setId(1L);

        Flight resultingFlight = FlightMapper.flightUpdateDTOToFlight(flightUpdateDTO);
        when(flightRepository.findById(1L)).thenReturn(Optional.of(resultingFlight));
        when(cityRepository.findById(1L)).thenReturn(Optional.of(departureCity));
        when(cityRepository.findById(2L)).thenReturn(Optional.of(arrivalCity));
        when(flightRepository.save(any(Flight.class))).thenReturn(resultingFlight);

        FlightResponseDTO flightResponseDTO = FlightMapper.flightToFlightResponseDTO(resultingFlight);

        FlightOperationWrapper<FlightResponseDTO> flightOperationWrapper = flightService.updateFlight(flightUpdateDTO);
        assertEquals(flightOperationWrapper.getResult(), flightResponseDTO);

        verify(cityRepository).findById(1L);
        verify(cityRepository).findById(2L);
        verify(flightRepository).findById(1L);
        verify(flightRepository).save(any(Flight.class));

    }

    @Test
    @DisplayName("updateFlight should throw EntityNotFound")
    void updateFlightShouldThrowEntityNotFound() {
        FlightUpdateDTO flightUpdateDTO = new FlightUpdateDTO();
        flightUpdateDTO.setId(1L);
        flightUpdateDTO.setFlightName("test");
        flightUpdateDTO.setDepartureCityId(1L);
        flightUpdateDTO.setArrivalCityId(2L);
        flightUpdateDTO.setFlightType("test");
        City arrivalCity = new City();
        arrivalCity.setId(2L);
        City departureCity = new City();
        departureCity.setId(1L);

        when(flightRepository.findById(1L)).thenReturn(Optional.empty());
        when(cityRepository.findById(1L)).thenReturn(Optional.of(departureCity));
        when(cityRepository.findById(2L)).thenReturn(Optional.of(arrivalCity));
        assertThrows(EntityNotFoundException.class, () -> flightService.updateFlight(flightUpdateDTO));


        verify(cityRepository).findById(1L);
        verify(cityRepository).findById(2L);
        verify(flightRepository).findById(1L);
    }

    @Test
    @DisplayName("Should test deleteFlight method of FlightServiceImpl")
    void shouldDeleteFlight() throws EntityNotFoundException {
        Flight flight = FlightBuilder.random().build();
        Set<Flight> flights = new HashSet<>(Arrays.asList(flight));
        City city = new City();
        city.setArrivalFlights(flights);
        city.setDepartureFlights(flights);

        when(flightRepository.findById(1L)).thenReturn(Optional.of(flight));
        when(cityRepository.getArrivalCityByFlightId(anyLong())).thenReturn(Optional.of(city));
        when(cityRepository.getDepartureCityByFlightId(anyLong())).thenReturn(Optional.of(city));

        FlightOperationWrapper<FlightResponseDTO> flightOperationWrapper = flightService.deleteFlight(1L);
        assertEquals(flightOperationWrapper.getResult(), FlightMapper.flightToFlightResponseDTO(flight));

        verify(flightRepository).findById(1L);
        verify(cityRepository, times(1)).getArrivalCityByFlightId(anyLong());
        verify(cityRepository, times(1)).getDepartureCityByFlightId(anyLong());
    }

    @Test
    @DisplayName("deleteFlight should throw EntityNotFound")
    void deleteFlightShouldThrowEntityNotFoundException() {
        when(flightRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> flightService.deleteFlight(1L));

        verify(flightRepository).findById(1L);
    }

    @Test
    @DisplayName("Should add schedule to flight")
    void shouldAddScheduleToFlight() throws EntityNotFoundException {
        Flight flight = FlightBuilder.random().build();
        Schedule schedule = new Schedule();
        schedule.setScheduleId(1L);
        schedule.setLandingTime(LocalTime.of(10, 0));
        schedule.setDepartureTime(LocalTime.of(10, 0));
        schedule.setStop("Somewhere");
        flight.setFlightSchedule(schedule);

        ScheduleInsertDTO scheduleInsertDTO = new ScheduleInsertDTO();
        scheduleInsertDTO.setDepartureTime(schedule.getDepartureTime());
        scheduleInsertDTO.setLandingTime(schedule.getLandingTime());
        scheduleInsertDTO.setStop(schedule.getStop());

        when(flightRepository.findById(anyLong())).thenReturn(Optional.of(flight));
        when(flightRepository.save(any(Flight.class))).thenReturn(flight);
        when(scheduleRepository.save(any(Schedule.class))).thenReturn(schedule);

        ScheduleResponseDTO scheduleResponseDTO = FlightMapper.scheduleToScheduleResponseDTO(schedule, flight.getFlightId());

        ScheduleResponseDTO actualScheduleResponseDTO = flightService.addSchedule(flight.getFlightId(), scheduleInsertDTO);

        assertEquals(scheduleResponseDTO, actualScheduleResponseDTO);

        verify(flightRepository).findById(anyLong());
        verify(flightRepository).save(any(Flight.class));
    }

    @Test
    @DisplayName("addSchedule should throw EntityNotFound")
    void addScheduleShouldThrowEntityNotFoundException() {
        ScheduleInsertDTO scheduleInsertDTO = new ScheduleInsertDTO();
        scheduleInsertDTO.setDepartureTime(LocalTime.of(10, 0));
        scheduleInsertDTO.setLandingTime(LocalTime.of(10, 0));
        scheduleInsertDTO.setStop("Somewhere");
        when(flightRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> flightService.addSchedule(1L, scheduleInsertDTO));

        verify(flightRepository).findById(1L);
    }


    @Test
    @DisplayName("Should test getFlightsByFlightName method of FlightServiceImpl")
    void shouldReturnFlightsByFlightName() throws EntityNotFoundException {
        Flight flight = FlightBuilder.random().build();
        Flight flight2 = FlightBuilder.random().build();

        List<Flight> flights = Arrays.asList(flight, flight2);

        when(flightRepository.findByFlightName(anyString())).thenReturn(flights);

        FlightResponseDTO flightResponseDTO = FlightMapper.flightToFlightResponseDTO(flight);
        FlightResponseDTO flightResponseDTO2 = FlightMapper.flightToFlightResponseDTO(flight2);

        List<FlightResponseDTO> flightResponseDTOS = flightService.getFlightsByFlightName("test");
        assertIterableEquals(flightResponseDTOS, Arrays.asList(flightResponseDTO, flightResponseDTO2));

        verify(flightRepository).findByFlightName(anyString());
    }


    @Test
    @DisplayName("Should test getFlightsByDepartureCity method of FlightServiceImpl")
    void shouldReturnFlightsByDepartureCity() {
        Flight flight = FlightBuilder.random().build();
        Flight flight2 = FlightBuilder.random().build();

        List<Flight> flights = Arrays.asList(flight, flight2);

        when(cityRepository.findByDepartureCityLike(anyString())).thenReturn(flights);

        FlightResponseDTO flightResponseDTO = FlightMapper.flightToFlightResponseDTO(flight);
        FlightResponseDTO flightResponseDTO2 = FlightMapper.flightToFlightResponseDTO(flight2);

        List<FlightResponseDTO> flightResponseDTOS = flightService.getFlightsByDepartureCity("test");
        assertIterableEquals(flightResponseDTOS, Arrays.asList(flightResponseDTO, flightResponseDTO2));

        verify(cityRepository).findByDepartureCityLike(anyString());
    }

    @Test
    @DisplayName("Should test getFlightsByArrivalCity method of FlightServiceImpl")
    void shouldReturnFlightsByArrivalCity() {
        Flight flight = FlightBuilder.random().build();
        Flight flight2 = FlightBuilder.random().build();

        List<Flight> flights = Arrays.asList(flight, flight2);

        when(cityRepository.findByArrivalCityLike(anyString())).thenReturn(flights);

        FlightResponseDTO flightResponseDTO = FlightMapper.flightToFlightResponseDTO(flight);
        FlightResponseDTO flightResponseDTO2 = FlightMapper.flightToFlightResponseDTO(flight2);

        List<FlightResponseDTO> flightResponseDTOS = flightService.getFlightsByArrivalCity("test");
        assertIterableEquals(flightResponseDTOS, Arrays.asList(flightResponseDTO, flightResponseDTO2));

        verify(cityRepository).findByArrivalCityLike(anyString());
    }

    @Test
    @DisplayName("Should test getFlightsByFlightType method of FlightServiceImpl")
    void shouldReturnFlightsByFlightType() {
        Flight flight = FlightBuilder.random().build();
        Flight flight2 = FlightBuilder.random().build();

        List<Flight> flights = Arrays.asList(flight, flight2);

        when(flightRepository.findByFlightType(anyString())).thenReturn(flights);

        FlightResponseDTO flightResponseDTO = FlightMapper.flightToFlightResponseDTO(flight);
        FlightResponseDTO flightResponseDTO2 = FlightMapper.flightToFlightResponseDTO(flight2);

        List<FlightResponseDTO> flightResponseDTOS = flightService.getFlightsByFlightType("test");
        assertIterableEquals(flightResponseDTOS, Arrays.asList(flightResponseDTO, flightResponseDTO2));

        verify(flightRepository).findByFlightType(anyString());
    }


    @Test
    @DisplayName("Should test getAllFlight")
    void getAllFlight() {
        Flight flight = FlightBuilder.random().build();
        Flight flight2 = FlightBuilder.random().build();
        List<Flight> flights = Arrays.asList(flight, flight2);
        when(flightRepository.findAll()).thenReturn(flights);
        List<Flight> flightsResult = flightService.getAllFlight();
        assertIterableEquals(flightsResult, flights);
        verify(flightRepository).findAll();
    }

    @Test
    @DisplayName("Should test getAllFlightIds")
    void getAllFlightIds() {
        Flight flight = FlightBuilder.random().build();
        Flight flight2 = FlightBuilder.random().build();

        List<Flight> flights = Arrays.asList(flight, flight2);
        List<Long> flightsIds = flights.stream().map(Flight::getFlightId).collect(Collectors.toList());

        when(flightRepository.findAllIds()).thenReturn(flightsIds);

        List<Long> flightsResult = flightService.getAllFlightIds();
        assertIterableEquals(flightsResult, flightsIds);

        verify(flightRepository).findAllIds();
    }

    @Test
    @DisplayName("Should test getFlightModelById")
    void getFlightModelById() {
        Flight flight = FlightBuilder.random().build();
        when(flightRepository.findById(anyLong())).thenReturn(Optional.of(flight));
        Optional<Flight> flightResult = flightService.getFlightModelById(1L);

        assertTrue(flightResult.isPresent());
        assertEquals(flightResult.get(), flight);

        verify(flightRepository).findById(anyLong());
    }

    @Test
    @DisplayName("Should test getNewSeatId")
    void getNewSeatId() throws EntityNotFoundException {
        Seat seat = new Seat(1L);
        Flight flight = FlightBuilder.random().setSeats(new ArrayList<>(Arrays.asList(seat))).build();
        when(seatRepository.save(any(Seat.class))).thenReturn(seat);
        when(flightRepository.findById(anyLong())).thenReturn(Optional.of(flight));
        Long seatId = flightService.getNewSeatId(1L);

        assertEquals(1L, seatId);

        verify(seatRepository).save(any(Seat.class));
        verify(flightRepository).findById(anyLong());
    }

    @Test
    @DisplayName("Should test countSeatsByFlightId")
    void countSeatsByFlightId() {
        Flight flight = FlightBuilder.random().build();
        when(flightRepository.countSeatsByFlightId(anyLong())).thenReturn((long) flight.getSeats().size());
        Long seatsCount = flightService.countSeatsByFlightId(1L);

        assertEquals(flight.getSeats().size(), seatsCount);

        verify(flightRepository).countSeatsByFlightId(anyLong());
    }

    @Test
    @DisplayName("Should test findFlightsByIds")
    void findFlightsByIds() {
        Flight flight = FlightBuilder.random().build();
        Flight flight2 = FlightBuilder.random().build();
        List<Flight> flights = Arrays.asList(flight, flight2);
        List<Long> flightsIds = flights.stream().map(Flight::getFlightId).collect(Collectors.toList());
        when(flightRepository.findAllById(anyList()))
                .thenReturn(flights);

        List<Flight> flightsResult = flightService.findFlightsByIds(flightsIds);

        assertIterableEquals(flightsResult, flights);

        verify(flightRepository).findAllById(anyList());
    }

    @Test
    @DisplayName("Should test checkIfFlightExists")
    void checkIfFlightExists() {
        Flight flight = FlightBuilder.random().setFlightId(1L).build();
        when(flightRepository.existsById(anyLong())).thenReturn(true);
        boolean flightExists = flightService.checkIfFlightExists(1L);

        assertTrue(flightExists);

        verify(flightRepository).existsById(anyLong());
    }

    @Test
    @DisplayName("Should test getFlightById")
    void getFlightById() throws EntityNotFoundException {
        Flight flight = FlightBuilder.random().setFlightId(3L).build();
        when(flightRepository.findById(3L)).thenReturn(Optional.of(flight));
        FlightResponseDTO flightResponseDTO = flightService.getFlightById(3L);

        assertEquals(flightResponseDTO.getFlightNumber(), flight.getFlightId());

        verify(flightRepository).findById(anyLong());
    }

    @Test
    @DisplayName("Should test getFlightEntityById")
    void getFlightEntityById() throws EntityNotFoundException {
        Flight flight = FlightBuilder.random().build();

        when(flightRepository.findById(anyLong())).thenReturn(Optional.of(flight));

        Flight flightResult = flightService.getFlightEntityById(1L);

        assertEquals(flightResult, flight);

        verify(flightRepository).findById(anyLong());
    }
}