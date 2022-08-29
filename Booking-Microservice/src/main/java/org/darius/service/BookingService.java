package org.darius.service;

import org.darius.dto.request.insert.AdminBookingInsertDTO;
import org.darius.dto.request.insert.UserBookingInsertDTO;
import org.darius.dto.response.AdminBookingResponseDTO;
import org.darius.dto.response.UserDetailsResponseDTO;
import org.darius.exception.EntityNotFoundException;
import org.darius.exception.SeatCapacityExceededException;
import org.darius.wrapper.ListWrapper;

import javax.transaction.Transactional;
import java.util.List;

public interface BookingService {
    @Transactional
    AdminBookingResponseDTO addBooking(AdminBookingInsertDTO adminBookingInsertDTO, Long flightNumber) throws EntityNotFoundException, SeatCapacityExceededException;
    @Transactional
    AdminBookingResponseDTO addBooking(UserBookingInsertDTO userBookingInsertDTO, String username, Long flightId) throws SeatCapacityExceededException, EntityNotFoundException;

    @Transactional
    ListWrapper<List<AdminBookingResponseDTO>> getFlightPassengerBookingListByUserId(Long userId) throws EntityNotFoundException;

    @Transactional
    ListWrapper<List<AdminBookingResponseDTO>> getBookingListByFlightId(Long flightId) throws EntityNotFoundException;
    @Transactional
    AdminBookingResponseDTO addPassengerWithRandomBooking(AdminBookingInsertDTO adminBookingInsertDTO, Long flightNumber)
            throws EntityNotFoundException;

    UserDetailsResponseDTO getPassengerDetailsByBookingId(Long bookingId) throws EntityNotFoundException;

    List<Long> findFlightIdsByBookingId(Long bookingId);

    ListWrapper<List<AdminBookingResponseDTO>> getPassengerBookingHistory(Long userId) throws EntityNotFoundException;

    ListWrapper<List<AdminBookingResponseDTO>> getPassengerBookingHistory(String username) throws EntityNotFoundException;
}
