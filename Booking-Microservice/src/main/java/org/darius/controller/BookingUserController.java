package org.darius.controller;

import org.darius.dto.request.insert.UserBookingInsertDTO;
import org.darius.dto.response.AdminBookingResponseDTO;
import org.darius.exception.EntityNotFoundException;
import org.darius.exception.SeatCapacityExceededException;
import org.darius.service.BookingService;
import org.darius.wrapper.ListWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/booking/user")
public class BookingUserController {
    private final Logger logger = LoggerFactory.getLogger(BookingUserController.class);
    @Autowired
    private BookingService bookingService;

    @PostMapping("/{flightId}")
    public ResponseEntity<AdminBookingResponseDTO> addBooking(@RequestBody @Valid UserBookingInsertDTO userBookingInsertDTO,
                                                              @PathVariable Long flightId) throws EntityNotFoundException, SeatCapacityExceededException {
        logger.info("Adding passenger to flight");

        String username = getUsername();

        AdminBookingResponseDTO adminBookingResponseDTO = bookingService.addBooking(userBookingInsertDTO, username, flightId);
        if (adminBookingResponseDTO == null) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(adminBookingResponseDTO, HttpStatus.CREATED);
    }

    @GetMapping("/passenger/history")
    public ResponseEntity<ListWrapper<List<AdminBookingResponseDTO>>> getBookingHistory() throws EntityNotFoundException {
        logger.info("Getting passenger booking history");

        String username = getUsername();

        ListWrapper<List<AdminBookingResponseDTO>> adminBookingResponseDTOListWrapper =
                bookingService.getPassengerBookingHistory(username);
        if (adminBookingResponseDTOListWrapper.getList() == null) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(adminBookingResponseDTOListWrapper, HttpStatus.OK);
    }

    private String getUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

}