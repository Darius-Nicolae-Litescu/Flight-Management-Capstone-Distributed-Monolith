package org.darius.controller;

import org.darius.dto.request.insert.AdminBookingInsertDTO;
import org.darius.dto.response.AdminBookingResponseDTO;
import org.darius.dto.response.UserDetailsResponseDTO;
import org.darius.exception.EntityNotFoundException;
import org.darius.exception.SeatCapacityExceededException;
import org.darius.service.BookingService;
import org.darius.wrapper.ListWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/booking/admin")
public class BookingAdminController {
    private final Logger logger = LoggerFactory.getLogger(BookingAdminController.class);
    @Autowired
    private BookingService bookingService;

    @PostMapping("/{flightId}")
    public ResponseEntity<AdminBookingResponseDTO> addPassengerToFlight(@RequestBody @Valid AdminBookingInsertDTO adminBookingInsertDTO,
                                                                        @PathVariable Long flightId) throws EntityNotFoundException, SeatCapacityExceededException {
        logger.info("Adding passenger to flight");
        AdminBookingResponseDTO adminBookingResponseDTO = bookingService.addBooking(adminBookingInsertDTO, flightId);
        if (adminBookingResponseDTO == null) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(adminBookingResponseDTO, HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ListWrapper<List<AdminBookingResponseDTO>>> getFlightPassengerBookingList(@PathVariable Long userId) throws EntityNotFoundException {
        logger.info("Getting flight passenger booking list");

        ListWrapper<List<AdminBookingResponseDTO>> adminBookingResponseDTOListWrapper =
                bookingService.getFlightPassengerBookingListByUserId(userId);
        if (adminBookingResponseDTOListWrapper.getList() == null) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(adminBookingResponseDTOListWrapper, HttpStatus.OK);

    }

    @GetMapping("/flight/{flightId}")
    public ResponseEntity<ListWrapper<List<AdminBookingResponseDTO>>> getFlightBookingList(@PathVariable Long flightId) throws EntityNotFoundException {
        logger.info("Getting flight passenger booking list");

        ListWrapper<List<AdminBookingResponseDTO>> adminBookingResponseDTOListWrapper =
                bookingService.getBookingListByFlightId(flightId);
        if (adminBookingResponseDTOListWrapper.getList() == null) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(adminBookingResponseDTOListWrapper, HttpStatus.OK);
    }

    @GetMapping("/passenger/{bookingId}")
    public ResponseEntity<UserDetailsResponseDTO> viewPassengerDetailsByBookingId(@PathVariable Long bookingId) throws EntityNotFoundException {
        UserDetailsResponseDTO userDetailsResponseDTO = bookingService.getPassengerDetailsByBookingId(bookingId);
        if (userDetailsResponseDTO == null) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(userDetailsResponseDTO, HttpStatus.OK);
    }

    @GetMapping("/passenger/history/{userId}")
    public ResponseEntity<ListWrapper<List<AdminBookingResponseDTO>>> getPassengerBookingHistory(@PathVariable Long userId) throws EntityNotFoundException {
        logger.info("Getting passenger booking history");

        ListWrapper<List<AdminBookingResponseDTO>> adminBookingResponseDTOListWrapper =
                bookingService.getPassengerBookingHistory(userId);
        if (adminBookingResponseDTOListWrapper.getList() == null) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(adminBookingResponseDTOListWrapper, HttpStatus.OK);
    }

}