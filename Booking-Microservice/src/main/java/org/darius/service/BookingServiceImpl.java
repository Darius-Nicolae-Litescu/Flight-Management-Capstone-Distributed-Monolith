package org.darius.service;

import org.darius.dto.request.insert.AdminBookingInsertDTO;
import org.darius.dto.request.insert.UserBookingInsertDTO;
import org.darius.dto.response.AdminBookingResponseDTO;
import org.darius.dto.response.UserDetailsResponseDTO;
import org.darius.entity.Booking;
import org.darius.exception.EntityNotFoundException;
import org.darius.exception.SeatCapacityExceededException;
import org.darius.mapper.PassengerMapper;
import org.darius.model.Flight;
import org.darius.model.Passenger;
import org.darius.model.User;
import org.darius.repository.BookingRepository;
import org.darius.wrapper.ListWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

@Service
public class BookingServiceImpl implements BookingService {
    private final Logger logger = LoggerFactory.getLogger(BookingServiceImpl.class);
    private final BookingRepository bookingRepository;

    private final FlightService flightService;

    private final UserService userService;

    @Autowired
    public BookingServiceImpl(BookingRepository bookingRepository, FlightService flightService, UserService userService) {
        this.bookingRepository = bookingRepository;
        this.flightService = flightService;
        this.userService = userService;
    }

    @Override
    @Transactional
    public AdminBookingResponseDTO addBooking(AdminBookingInsertDTO adminBookingInsertDTO, Long flightNumber) throws EntityNotFoundException, SeatCapacityExceededException {
        logger.info("Adding passenger: {} | to flight: {}", adminBookingInsertDTO, flightNumber);

        Optional<Flight> flightOptional = flightService.getFlightById(flightNumber);
        if (!flightOptional.isPresent()) {
            throw new EntityNotFoundException(flightNumber);
        }
        Optional<User> userOptional = userService.getUserById(adminBookingInsertDTO.getUserId());
        if (!userOptional.isPresent()) {
            throw new EntityNotFoundException(adminBookingInsertDTO.getUserId());
        }
        Long currentSeatCount = flightService.countSeatsByFlightId(flightNumber);
        if (currentSeatCount + adminBookingInsertDTO.getNumberOfSeats() > flightOptional.get().getFlightSeatCapacity()) {
            throw new SeatCapacityExceededException("Flight seat capacity exceeded, retry with another value or check capacity");
        }
        Flight flight = flightOptional.get();
        Date currentDate = Date.from(LocalDateTime.now().toInstant(ZoneOffset.UTC));
        Booking booking = new Booking(currentDate);

        Long seatId = flightService.getNewSeatId(flight.getFlightId());

        booking.setSeatId(seatId);
        booking.setFlightId(flight.getFlightId());
        booking.setUserId(userOptional.get().getUserId());
        booking = bookingRepository.save(booking);

        logger.info("Added passenger successfully to flight {}", flightNumber);

        AdminBookingResponseDTO adminBookingResponseDTO = new AdminBookingResponseDTO();

        Passenger passenger = userOptional.get().getPassenger();
        adminBookingResponseDTO.setBookingId(booking.getBookingId());
        adminBookingResponseDTO.setFullName(passenger.getFirstName() + " " + passenger.getLastName());
        adminBookingResponseDTO.setDateOfBooking(currentDate);
        adminBookingResponseDTO.setFlightName(flightOptional.get().getFlightName());
        return adminBookingResponseDTO;
    }

    @Override
    public AdminBookingResponseDTO addBooking(UserBookingInsertDTO userBookingInsertDTO, String username, Long flightId) throws SeatCapacityExceededException, EntityNotFoundException {
        logger.info("Adding passenger: {} | to flight: {}", userBookingInsertDTO, flightId);

        Optional<Flight> flightOptional = flightService.getFlightById(flightId);
        if (!flightOptional.isPresent()) {
            throw new EntityNotFoundException(flightId);
        }
        Optional<User> userOptional = userService.getUserAndPassengerDetailsByJWT(username);
        if (!userOptional.isPresent()) {
            throw new EntityNotFoundException("User not found, username: " + username, 0L, User.class.getName());
        }
        Long currentSeatCount = flightService.countSeatsByFlightId(flightId);
        if (currentSeatCount + userBookingInsertDTO.getNumberOfSeats() > flightOptional.get().getFlightSeatCapacity()) {
            throw new SeatCapacityExceededException("Flight seat capacity exceeded, retry with another value or check capacity");
        }
        Flight flight = flightOptional.get();
        Date currentDate = Date.from(LocalDateTime.now().toInstant(ZoneOffset.UTC));
        Booking booking = new Booking(currentDate);

        Long seatId = flightService.getNewSeatId(flight.getFlightId());

        booking.setSeatId(seatId);
        booking.setFlightId(flight.getFlightId());
        booking.setUserId(userOptional.get().getUserId());
        booking = bookingRepository.save(booking);

        logger.info("Added passenger successfully to flight {}", flightId);

        AdminBookingResponseDTO adminBookingResponseDTO = new AdminBookingResponseDTO();

        Passenger passenger = userOptional.get().getPassenger();
        adminBookingResponseDTO.setBookingId(booking.getBookingId());
        adminBookingResponseDTO.setFullName(passenger.getFirstName() + " " + passenger.getLastName());
        adminBookingResponseDTO.setDateOfBooking(currentDate);
        adminBookingResponseDTO.setFlightName(flightOptional.get().getFlightName());
        return adminBookingResponseDTO;
    }

    @Override
    public ListWrapper<List<AdminBookingResponseDTO>> getFlightPassengerBookingListByUserId(Long userId) throws EntityNotFoundException {
        logger.info("Retrieving passenger booking list for user id: {}", userId);

        Optional<User> userOptional = userService.getUserById(userId);
        if (!userOptional.isPresent()) {
            throw new EntityNotFoundException(userId);
        }
        List<Booking> bookingList = bookingRepository.bookingListByUserId(userOptional.get().getUserId());


        Map<Long, Long> bookingFlightMap = new HashMap<>();
        bookingList.forEach(booking -> {
            bookingFlightMap.put(booking.getBookingId(), booking.getFlightId());
        });

        Map<Booking, List<Flight>> bookingFlightMapWithPassenger = new HashMap<>();
        bookingList.forEach(booking -> {
            List<Flight> flightList = new ArrayList<>();
            flightList.add(flightService.getFlightById(bookingFlightMap.get(booking.getBookingId())).get());
            bookingFlightMapWithPassenger.put(booking, flightList);
        });


        List<AdminBookingResponseDTO> adminBookingResponseDTOS = new ArrayList<>();
        Passenger passenger = userOptional.get().getPassenger();

        bookingFlightMapWithPassenger.forEach((booking, flightList) -> {
            AdminBookingResponseDTO adminBookingResponseDTO = new AdminBookingResponseDTO();
            adminBookingResponseDTO.setFullName(passenger.getFirstName() + " " + passenger.getLastName());
            adminBookingResponseDTO.setDateOfBooking(booking.getDateOfBooking());
            adminBookingResponseDTO.setFlightName(flightList.get(0).getFlightName());
            adminBookingResponseDTOS.add(adminBookingResponseDTO);
        });

        logger.info("Retrieved booking information sucesfully: {}", adminBookingResponseDTOS);

        Long numberOfResults = Long.valueOf(adminBookingResponseDTOS.size());
        return new ListWrapper<>(numberOfResults, adminBookingResponseDTOS);

    }

    @Override
    public ListWrapper<List<AdminBookingResponseDTO>> getPassengerBookingHistory(Long userId) throws EntityNotFoundException {
        logger.info("Retrieving passenger booking history for user id: {}", userId);
        Optional<User> userOptional = userService.getUserById(userId);
        if (!userOptional.isPresent()) {
            throw new EntityNotFoundException(userId);
        }
        List<Booking> bookingList = bookingRepository.bookingListByUserId(userOptional.get().getUserId());
        List<AdminBookingResponseDTO> adminBookingResponseDTOS = new ArrayList<>();
        Passenger passenger = userOptional.get().getPassenger();
        bookingList.forEach(booking -> {
            AdminBookingResponseDTO adminBookingResponseDTO = new AdminBookingResponseDTO();
            adminBookingResponseDTO.setFullName(passenger.getFirstName() + " " + passenger.getLastName());
            adminBookingResponseDTO.setDateOfBooking(booking.getDateOfBooking());
            adminBookingResponseDTO.setFlightName(flightService.getFlightById(booking.getFlightId()).get().getFlightName());
            adminBookingResponseDTOS.add(adminBookingResponseDTO);
        });
        Long numberOfResults = Long.valueOf(adminBookingResponseDTOS.size());
        return new ListWrapper<>(numberOfResults, adminBookingResponseDTOS);
    }

    @Override
    public ListWrapper<List<AdminBookingResponseDTO>> getPassengerBookingHistory(String username) throws EntityNotFoundException {
        logger.info("Retrieving passenger booking history for username: {}", username);
        Optional<User> userOptional = userService.getUserAndPassengerDetailsByJWT(username);
        if (!userOptional.isPresent()) {
            throw new EntityNotFoundException("User not found, username: " + username, 0L, User.class.getName());
        }
        List<Booking> bookingList = bookingRepository.bookingListByUserId(userOptional.get().getUserId());
        List<AdminBookingResponseDTO> adminBookingResponseDTOS = new ArrayList<>();
        Passenger passenger = userOptional.get().getPassenger();
        bookingList.forEach(booking -> {
            AdminBookingResponseDTO adminBookingResponseDTO = new AdminBookingResponseDTO();
            adminBookingResponseDTO.setFullName(passenger.getFirstName() + " " + passenger.getLastName());
            adminBookingResponseDTO.setDateOfBooking(booking.getDateOfBooking());
            adminBookingResponseDTO.setFlightName(flightService.getFlightById(booking.getFlightId()).get().getFlightName());
            adminBookingResponseDTOS.add(adminBookingResponseDTO);
        });
        Long numberOfResults = Long.valueOf(adminBookingResponseDTOS.size());
        return new ListWrapper<>(numberOfResults, adminBookingResponseDTOS);
    }


    @Override
    public ListWrapper<List<AdminBookingResponseDTO>> getBookingListByFlightId(Long flightId) throws EntityNotFoundException {
        logger.info("Retrieving passenger booking list for flight id: {}", flightId);

        Optional<Flight> flightOptional = flightService.getFlightById(flightId);
        if (!flightOptional.isPresent()) {
            throw new EntityNotFoundException(flightId);
        }

        List<Booking> bookingList = bookingRepository.bookingListByFlightId(flightId);

        List<AdminBookingResponseDTO> adminBookingResponseDTOS = new ArrayList<>();
        for (Booking booking : bookingList) {
            Optional<Flight> flight = flightService.getFlightById(flightId);
            if (!flight.isPresent()) {
                throw new EntityNotFoundException(flightId);
            }
            String flightName = flight.get().getFlightName();

            Optional<User> userOptional = userService.getUserById(booking.getUserId());
            if (!userOptional.isPresent()) {
                throw new EntityNotFoundException(booking.getUserId());
            }
            Passenger passenger = userOptional.get().getPassenger();
            String fullName = passenger.getFirstName() + " " + passenger.getLastName();

            AdminBookingResponseDTO adminBookingResponseDTO = new AdminBookingResponseDTO();
            adminBookingResponseDTO.setFullName(fullName);
            adminBookingResponseDTO.setBookingId(booking.getBookingId());
            adminBookingResponseDTO.setDateOfBooking(booking.getDateOfBooking());
            adminBookingResponseDTO.setFlightName(flightName);
            adminBookingResponseDTOS.add(adminBookingResponseDTO);
        }

        logger.info("Retrieved booking information sucesfully: {}", adminBookingResponseDTOS);

        Long numberOfResults = Long.valueOf(adminBookingResponseDTOS.size());
        return new ListWrapper<>(numberOfResults, adminBookingResponseDTOS);
    }


    @Override
    public UserDetailsResponseDTO getPassengerDetailsByBookingId(Long bookingId) throws EntityNotFoundException {
        logger.info("Retrieving booking with id: {}", bookingId);

        Optional<Booking> bookingOptional = bookingRepository.findById(bookingId);
        if (!bookingOptional.isPresent()) {
            throw new EntityNotFoundException(bookingId);
        }

        Long userId = bookingRepository.findUserIdByBookingId(bookingId);
        Optional<User> userOptional = userService.getUserById(userId);
        if (!userOptional.isPresent()) {
            throw new EntityNotFoundException(bookingId);
        }

        UserDetailsResponseDTO userDetailsResponseDTO = PassengerMapper.convertUserToUserDetailsResponseDto(userOptional.get(),
                Arrays.asList(bookingOptional.get()));
        logger.info("Retrieved user details sucesfully: {}", userOptional.get());

        return userDetailsResponseDTO;
    }

    @Override
    public List<Long> findFlightIdsByBookingId(Long bookingId) {
        return bookingRepository.flightIdsByBookingId(bookingId);
    }

    @Override
    public AdminBookingResponseDTO addPassengerWithRandomBooking(AdminBookingInsertDTO adminBookingInsertDTO, Long flightNumber) throws EntityNotFoundException {
        return null;
    }

}
