package org.darius.service;

import org.darius.dto.request.PassengerAdminInsertDTO;
import org.darius.dto.request.PassengerAdminUpdateDTO;
import org.darius.dto.request.PassengerUserInsertDTO;
import org.darius.dto.request.PassengerUserUpdateDTO;
import org.darius.dto.response.PassengerResponseDTO;
import org.darius.exception.EntityNotFoundException;

import java.util.Optional;

public interface PassengerService {

    Optional<PassengerResponseDTO> addPassenger(PassengerAdminInsertDTO passengerAdminInsertDTO) throws EntityNotFoundException;

    Optional<PassengerResponseDTO> addPassenger(PassengerUserInsertDTO passengerUserInsertDTO, String username) throws EntityNotFoundException;

    Optional<PassengerResponseDTO> updatePassenger(PassengerAdminUpdateDTO passengerAdminUpdateDTO) throws EntityNotFoundException;

    Optional<PassengerResponseDTO> updatePassenger(PassengerUserUpdateDTO passengerUserUpdateDTO, String username) throws EntityNotFoundException;


    Optional<PassengerResponseDTO> getPassengerById(Long id) throws EntityNotFoundException;

    Optional<PassengerResponseDTO> getPassengerByUserId(Long userId) throws EntityNotFoundException;

    Optional<PassengerResponseDTO> getPassengerByUsername(String username) throws EntityNotFoundException;
}
