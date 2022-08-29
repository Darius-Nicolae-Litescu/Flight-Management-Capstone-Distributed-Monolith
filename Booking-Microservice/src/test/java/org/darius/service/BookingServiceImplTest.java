package org.darius.service;

import static org.junit.jupiter.api.Assertions.*;

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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class BookingServiceImplTest {
    @Mock
    private BookingRepository bookingRepository;
    @Mock
    private FlightService flightService;
    @Mock
    private UserService userService;

    @InjectMocks
    private BookingServiceImpl bookingService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Should test addPassengerToFlight")
    void shouldAddPassengerToFlight() throws EntityNotFoundException, SeatCapacityExceededException {
        Flight flight = FlightBuilder.random().setFlightSeatCapacity(2000).build();
        User user = UserBuilder.random().build();
        Booking booking = BookingBuilder.random().build();

        when(bookingRepository.save(any())).thenReturn(booking);
        when(flightService.getFlightById(anyLong())).thenReturn(Optional.of(flight));
        when(userService.getUserById(anyLong())).thenReturn(Optional.of(user));
        when(bookingRepository.save(any())).thenReturn(booking);

        AdminBookingInsertDTO adminBookingInsertDTO = new AdminBookingInsertDTO();
        adminBookingInsertDTO.setUserId(1L);
        adminBookingInsertDTO.setNumberOfSeats(2L);

        AdminBookingResponseDTO adminBookingResponseDTO = bookingService.addBooking(adminBookingInsertDTO, flight.getFlightId());
        assertEquals(booking.getBookingId(), adminBookingResponseDTO.getBookingId());

        verify(flightService).getFlightById(anyLong());
        verify(userService).getUserById(anyLong());
        verify(bookingRepository).save(any());
    }

    @Test
    @DisplayName("addPassengerToFlight should throw exception if flight not found")
    void addPassengerToFlightShouldThrowExceptionIfFlightNotFound() throws EntityNotFoundException, SeatCapacityExceededException {
        Flight flight = FlightBuilder.random().build();
        Booking booking = BookingBuilder.random().build();

        when(bookingRepository.save(any())).thenReturn(booking);
        when(flightService.getFlightById(anyLong())).thenReturn(Optional.empty());
        when(bookingRepository.save(any())).thenReturn(booking);
        AdminBookingInsertDTO adminBookingInsertDTO = new AdminBookingInsertDTO();
        adminBookingInsertDTO.setUserId(1L);
        adminBookingInsertDTO.setNumberOfSeats(2L);

        assertThrows(EntityNotFoundException.class, () -> bookingService.addBooking(adminBookingInsertDTO, flight.getFlightId()));

        verify(flightService).getFlightById(anyLong());
        verify(bookingRepository, never()).save(any());
    }

    @Test
    @DisplayName("addPassengerToFlight should throw exception if seat capacity exceeded")
    void addPassengerToFlightShouldThrowSeatCapacityExceededException(){
        Flight flight = FlightBuilder.random().build();
        User user = UserBuilder.random().build();
        Booking booking = BookingBuilder.random().build();

        AdminBookingInsertDTO adminBookingInsertDTO = new AdminBookingInsertDTO();
        adminBookingInsertDTO.setUserId(1L);
        adminBookingInsertDTO.setNumberOfSeats(2000L);

        when(flightService.getFlightById(anyLong())).thenReturn(Optional.of(flight));
        when(userService.getUserById(anyLong())).thenReturn(Optional.of(user));
        when(bookingRepository.save(any())).thenReturn(booking);

        assertThrows(SeatCapacityExceededException.class, () -> bookingService.addBooking(adminBookingInsertDTO,  flight.getFlightId()));

        when(bookingRepository.save(any())).thenReturn(booking);
        when(flightService.getFlightById(anyLong())).thenReturn(Optional.of(flight));
        when(userService.getUserById(anyLong())).thenReturn(Optional.of(user));

    }


    @Test
    @DisplayName("Should test getFlightPassengerBookingListByUserId")
    void shouldGetFlightPassengerBookingListByUserId() throws EntityNotFoundException {
        Long userId = 1L;
        List<Booking> bookingList = new ArrayList<>();
        Booking booking = new Booking();
        booking.setFlightId(1L);
        booking.setBookingId(1L);
        bookingList.add(booking);
        Flight flight = new Flight();
        flight.setFlightId(1L);

        User user = new User();
        user.setUserId(1L);
        user.setPassenger(new Passenger());
        when(userService.getUserById(anyLong())).thenReturn(Optional.of(user));

        when(bookingRepository.bookingListByUserId(any())).thenReturn(bookingList);
        when(flightService.getFlightById(anyLong())).thenReturn(Optional.of(flight));

        ListWrapper<List<AdminBookingResponseDTO>> bookingListByUserId = bookingService.getFlightPassengerBookingListByUserId(userId);
        assertEquals(bookingList.size(), bookingListByUserId.getList().size());

        verify(userService).getUserById(anyLong());
        verify(bookingRepository).bookingListByUserId(any());
        verify(flightService).getFlightById(anyLong());
    }

    @Test
    @DisplayName("getFlightPassengerBookingListByUserId should throw exception if user not found")
    void getFlightPassengerBookingListByUserIdShouldThrowException(){
        Long userId = 1L;
        when(userService.getUserById(anyLong())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> bookingService.getFlightPassengerBookingListByUserId(userId));
        verify(userService).getUserById(anyLong());
    }

    @Test
    @DisplayName("Should test getPassengerBookingHistory")
    void shouldGetPassengerBookingHistory() throws EntityNotFoundException{
        User user = new User();
        user.setUserId(1L);
        user.setPassenger(new Passenger());
        when(userService.getUserById(anyLong())).thenReturn(Optional.of(user));
        when(bookingRepository.bookingListByUserId(any())).thenReturn(new ArrayList<>());

        ListWrapper<List<AdminBookingResponseDTO>> bookingListByUserId = bookingService.getPassengerBookingHistory(1L);
        assertEquals(0, bookingListByUserId.getList().size());

        verify(userService).getUserById(anyLong());
        verify(bookingRepository).bookingListByUserId(any());
    }

    @Test
    @DisplayName("getPassengerBookingHistory should throw exception if user not found")
    void getPassengerBookingHistoryShouldThrowException(){
        when(userService.getUserById(anyLong())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> bookingService.getPassengerBookingHistory(1L));
        verify(userService).getUserById(anyLong());
    }

    @Test
    @DisplayName("Should test getBookingListByFlightId")
    void shouldGetBookingListByFlightId() throws EntityNotFoundException{
        List<Booking> bookingList = new ArrayList<>();
        Booking booking = BookingBuilder.random().build();
        bookingList.add(booking);

        Flight flight = FlightBuilder.random().build();
        User user = UserBuilder.random().build();

        when(bookingRepository.bookingListByFlightId(any())).thenReturn(bookingList);
        when(flightService.getFlightById(any())).thenReturn(Optional.of(flight));
        when(userService.getUserById(any())).thenReturn(Optional.of(user));

        ListWrapper<List<AdminBookingResponseDTO>> bookingListByFlightId = bookingService.getBookingListByFlightId(booking.getFlightId());
        assertEquals(bookingList.size(), bookingListByFlightId.getList().size());

        verify(flightService, times(2)).getFlightById(any());
        verify(bookingRepository).bookingListByFlightId(booking.getFlightId());
        verify(userService).getUserById(any());
    }

    @Test
    @DisplayName("getBookingListByFlightId should throw exception if flight not found")
    void getBookingListByFlightIdShouldThrowException(){
        when(flightService.getFlightById(any())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> bookingService.getBookingListByFlightId(1L));
        verify(flightService).getFlightById(any());
    }

    @Test
    @DisplayName("Should test getPassengerDetailsByBookingId")
    void shouldGetPassengerDetailsByBookingId() throws EntityNotFoundException{
        Booking booking = BookingBuilder.random().build();
        User user = UserBuilder.random().build();

        when(bookingRepository.findById(anyLong())).thenReturn(Optional.of(booking));
        when(userService.getUserById(anyLong())).thenReturn(Optional.of(user));

        UserDetailsResponseDTO userDetailsResponseDTO = bookingService.getPassengerDetailsByBookingId(1L);
        assertEquals(user.getUsername(), userDetailsResponseDTO.getUsername());

        verify(bookingRepository).findById(anyLong());
        verify(userService).getUserById(anyLong());
    }

    @Test
    @DisplayName("getPassengerDetailsByBookingId should throw exception if booking not found")
    void getPassengerDetailsByBookingIdShouldThrowException(){
        when(bookingRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> bookingService.getPassengerDetailsByBookingId(1L));
        verify(bookingRepository).findById(anyLong());
    }

    @Test
    @DisplayName("Should test findFlightIdsByBookingId")
    void shouldFindFlightByBookingId(){
        List<Long> flightIds = new ArrayList<>();

        flightIds.add(1L);
        flightIds.add(2L);
        flightIds.add(3L);
        when(bookingRepository.flightIdsByBookingId(anyLong())).thenReturn(flightIds);

        List<Long> flightIdsByBookingId = bookingService.findFlightIdsByBookingId(1L);

        verify(bookingRepository).flightIdsByBookingId(anyLong());
    }

}