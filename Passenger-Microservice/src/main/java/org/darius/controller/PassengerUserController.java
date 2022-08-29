package org.darius.controller;

import org.darius.dto.request.PassengerAdminInsertDTO;
import org.darius.dto.request.PassengerUserInsertDTO;
import org.darius.dto.request.PassengerUserUpdateDTO;
import org.darius.dto.response.PassengerResponseDTO;
import org.darius.exception.EntityNotFoundException;
import org.darius.service.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/passenger/user")
public class PassengerUserController {

    private final PassengerService passengerService;

    @Autowired
    public PassengerUserController(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    @PostMapping("/add")
    public ResponseEntity<PassengerResponseDTO> addPassengerByJWTUsername(@RequestBody @Valid PassengerUserInsertDTO passengerUserInsertDTO) throws EntityNotFoundException {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Optional<PassengerResponseDTO> passenger = passengerService.addPassenger(passengerUserInsertDTO, username);
        if (!passenger.isPresent()) {
            return new ResponseEntity<>(passenger.get(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(passenger.get(), HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<PassengerResponseDTO> updatePassengerByJWTUsername(@RequestBody @Valid PassengerUserUpdateDTO passengerUserUpdateDTO) throws EntityNotFoundException {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<PassengerResponseDTO> passenger = passengerService.updatePassenger(passengerUserUpdateDTO, username);
        if (!passenger.isPresent()) {
            return new ResponseEntity<>(passenger.get(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(passenger.get(), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<PassengerResponseDTO> getPassengerByJWTUsername() throws EntityNotFoundException {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<PassengerResponseDTO> passenger = passengerService.getPassengerByUsername(username);
        if (!passenger.isPresent()) {
            return new ResponseEntity<>(passenger.get(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(passenger.get(), HttpStatus.OK);
    }


}
