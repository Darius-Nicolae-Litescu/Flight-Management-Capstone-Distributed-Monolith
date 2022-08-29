package org.darius.service;

import org.darius.dto.request.PassengerAdminInsertDTO;
import org.darius.dto.request.PassengerAdminUpdateDTO;
import org.darius.dto.request.PassengerUserInsertDTO;
import org.darius.dto.request.PassengerUserUpdateDTO;
import org.darius.dto.response.PassengerResponseDTO;
import org.darius.entity.Passenger;
import org.darius.exception.EntityNotFoundException;
import org.darius.mapper.PassengerMapper;
import org.darius.model.User;
import org.darius.repository.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PassengerServiceImpl implements PassengerService {

    private final PassengerRepository passengerRepository;

    private final UserService userService;

    @Autowired
    public PassengerServiceImpl(PassengerRepository passengerRepository, UserService userService) {
        this.passengerRepository = passengerRepository;
        this.userService = userService;
    }

    @Override
    public Optional<PassengerResponseDTO> addPassenger(PassengerAdminInsertDTO passengerAdminInsertDTO) throws EntityNotFoundException {
        if (!userService.getUserById(passengerAdminInsertDTO.getUserId()).isPresent()) {
            throw new EntityNotFoundException("User with id " + passengerAdminInsertDTO.getUserId() + " not found",
                    passengerAdminInsertDTO.getUserId(), User.class.toString());
        }
        Passenger passenger = PassengerMapper.passengerAdminInsertDtoToPassenger(passengerAdminInsertDTO);
        Passenger savedPassenger = passengerRepository.save(passenger);
        return Optional.of(PassengerMapper.passengerToPassengerResponseDto(savedPassenger));
    }

    @Override
    public Optional<PassengerResponseDTO> addPassenger(PassengerUserInsertDTO passengerUserInsertDTO, String username) throws EntityNotFoundException {
        Optional<User> userOptional = userService.getUserByUsername(username);
        if (!userOptional.isPresent()) {
            throw new EntityNotFoundException("User with username " + username + " not found", 0L, User.class.toString());
        }
        Passenger passenger = PassengerMapper.passengerUserInsertDtoToPassenger(passengerUserInsertDTO, userOptional.get().getUserId());
        Passenger savedPassenger = passengerRepository.save(passenger);
        return Optional.of(PassengerMapper.passengerToPassengerResponseDto(savedPassenger));
    }

    @Override
    public Optional<PassengerResponseDTO> updatePassenger(PassengerAdminUpdateDTO passengerAdminUpdateDTO) throws EntityNotFoundException {
        Optional<Passenger> passengerOptional = passengerRepository.findById(passengerAdminUpdateDTO.getId());
        if (!passengerOptional.isPresent()) {
            throw new EntityNotFoundException("Passenger with id " + passengerAdminUpdateDTO.getId() + " not found", passengerAdminUpdateDTO.getId(), Passenger.class.toString());
        }
        Passenger passenger = PassengerMapper.passengerAdminUpdateDtoToPassenger(passengerAdminUpdateDTO, passengerOptional.get());
        passengerRepository.save(passenger);
        return Optional.of(PassengerMapper.passengerToPassengerResponseDto(passenger));
    }

    @Override
    public Optional<PassengerResponseDTO> getPassengerById(Long id) throws EntityNotFoundException {
        Optional<Passenger> passenger = passengerRepository.findById(id);
        if (!passenger.isPresent()) {
            throw new EntityNotFoundException("Passenger with id " + id + " not found", id, Passenger.class.toString());
        }
        return Optional.of(PassengerMapper.passengerToPassengerResponseDto(passenger.get()));
    }

    @Override
    public Optional<PassengerResponseDTO> getPassengerByUserId(Long userId) throws EntityNotFoundException {
        Optional<Passenger> passenger = passengerRepository.findByUserId(userId);
        if (!passenger.isPresent()) {
            throw new EntityNotFoundException("Passenger with user id " + userId + " not found", userId, Passenger.class.toString());
        }
        return Optional.of(PassengerMapper.passengerToPassengerResponseDto(passenger.get()));
    }

    @Override
    public Optional<PassengerResponseDTO> getPassengerByUsername(String username) throws EntityNotFoundException {
        Optional<User> userOptional = userService.getUserByUsername(username);
        if (!userOptional.isPresent()) {
            throw new EntityNotFoundException("User with username " + username + " not found", 0L, User.class.toString());
        }
        Optional<Passenger> passenger = passengerRepository.findByUserId(userOptional.get().getUserId());
        if (!passenger.isPresent()) {
            throw new EntityNotFoundException("Passenger with user id " + userOptional.get().getUserId() + " not found", userOptional.get().getUserId(), Passenger.class.toString());
        }
        return Optional.of(PassengerMapper.passengerToPassengerResponseDto(passenger.get()));
    }

    @Override
    public Optional<PassengerResponseDTO> updatePassenger(PassengerUserUpdateDTO passengerUserUpdateDTO, String username) throws EntityNotFoundException {
        Optional<User> userOptional = userService.getUserByUsername(username);
        if (!userOptional.isPresent()) {
            throw new EntityNotFoundException("User with username " + username + " not found", 0L, User.class.toString());
        }

        Optional<Passenger> passengerOptional = passengerRepository.findByUserId(userOptional.get().getUserId());
        if (!passengerOptional.isPresent()) {
            throw new EntityNotFoundException("Passenger not found", passengerOptional.get().getId(), Passenger.class.toString());
        }

        Passenger passenger = PassengerMapper.passengerUserUpdateDtoToPassenger(passengerUserUpdateDTO, userOptional.get().getUserId(), passengerOptional.get());
        passengerRepository.save(passenger);
        return Optional.of(PassengerMapper.passengerToPassengerResponseDto(passenger));
    }
}
