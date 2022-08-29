package org.darius.controller;

import org.darius.dto.request.PassengerAdminUpdateDTO;
import org.darius.dto.request.PassengerAdminInsertDTO;
import org.darius.dto.response.PassengerResponseDTO;
import org.darius.exception.EntityNotFoundException;
import org.darius.service.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/passenger/admin")
public class PassengerAdminController {

    private final PassengerService passengerService;

    @Autowired
    public PassengerAdminController(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    @PostMapping("/add")
    public ResponseEntity<PassengerResponseDTO> addPassenger(@RequestBody @Valid PassengerAdminInsertDTO passengerAdminInsertDTO) throws EntityNotFoundException {
        Optional<PassengerResponseDTO> passenger = passengerService.addPassenger(passengerAdminInsertDTO);
        if (!passenger.isPresent()) {
            return new ResponseEntity<>(passenger.get(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(passenger.get(), HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<PassengerResponseDTO> updatePassenger(@RequestBody @Valid PassengerAdminUpdateDTO passengerAdminUpdateDTO) throws EntityNotFoundException {
        Optional<PassengerResponseDTO> passenger = passengerService.updatePassenger(passengerAdminUpdateDTO);
        if (!passenger.isPresent()) {
            return new ResponseEntity<>(passenger.get(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(passenger.get(), HttpStatus.OK);
    }

    @GetMapping("/{passengerId}")
    public ResponseEntity<PassengerResponseDTO> getPassengerById(@PathVariable Long passengerId) throws EntityNotFoundException {
        Optional<PassengerResponseDTO> passenger = passengerService.getPassengerById(passengerId);
        if (!passenger.isPresent()) {
            return new ResponseEntity<>(passenger.get(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(passenger.get(), HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<PassengerResponseDTO> getPassengerByUserId(@PathVariable Long userId) throws EntityNotFoundException {
        Optional<PassengerResponseDTO> passenger = passengerService.getPassengerByUserId(userId);
        if (!passenger.isPresent()) {
            return new ResponseEntity<>(passenger.get(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(passenger.get(), HttpStatus.OK);
    }


}
