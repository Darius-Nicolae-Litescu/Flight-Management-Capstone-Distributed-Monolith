package org.darius.service;

import org.darius.dto.request.PassengerAdminUpdateDTO;
import org.darius.dto.request.PassengerAdminInsertDTO;
import org.darius.dto.response.PassengerResponseDTO;
import org.darius.entity.Passenger;
import org.darius.exception.EntityNotFoundException;
import org.darius.model.User;
import org.darius.repository.PassengerRepository;
import org.darius.service.builder.PassengerBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class PassengerServiceImplTest {

    @Mock
    private PassengerRepository passengerRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private PassengerServiceImpl passengerService;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Should add passenger")
    void shouldAddPassenger() throws EntityNotFoundException {
        Passenger passenger = PassengerBuilder.random().build();

        PassengerAdminInsertDTO passengerAdminInsertDTO = new PassengerAdminInsertDTO();
        passengerAdminInsertDTO.setUserId(passenger.getUserId());
        passengerAdminInsertDTO.setFirstName(passenger.getPassengerDetails().getFirstName());
        passengerAdminInsertDTO.setLastName(passenger.getPassengerDetails().getLastName());
        passengerAdminInsertDTO.setAge(passenger.getPassengerDetails().getAge());
        passengerAdminInsertDTO.setGender(passenger.getPassengerDetails().getGender());
        passengerAdminInsertDTO.setPhoneNumber(passenger.getPassengerDetails().getPhoneNumber());

        PassengerResponseDTO passengerResponseDTO = new PassengerResponseDTO();
        passengerResponseDTO.setId(passenger.getId());
        passengerResponseDTO.setUserId(passenger.getUserId());
        passengerResponseDTO.setFirstName(passenger.getPassengerDetails().getFirstName());
        passengerResponseDTO.setLastName(passenger.getPassengerDetails().getLastName());
        passengerResponseDTO.setAge(passenger.getPassengerDetails().getAge());
        passengerResponseDTO.setGender(passenger.getPassengerDetails().getGender());
        passengerResponseDTO.setPhoneNumber(passenger.getPassengerDetails().getPhoneNumber());


        when(userService.getUserById(passengerAdminInsertDTO.getUserId())).thenReturn(Optional.of(new User()));
        when(passengerRepository.save(any(Passenger.class))).thenReturn(passenger);

        Optional<PassengerResponseDTO> passengerResponseDTOResult = passengerService.addPassenger(passengerAdminInsertDTO);

        assertTrue(passengerResponseDTOResult.isPresent());
        assertEquals(passengerResponseDTO, passengerResponseDTOResult.get());

        verify(passengerRepository, times(1)).save(any(Passenger.class));
    }

    @Test
    @DisplayName("addPassenger should throw EntityNotFoundException when user not found")
    void addPassengerShouldThrowEntityNotFoundException(){
        PassengerAdminInsertDTO passengerAdminInsertDTO = new PassengerAdminInsertDTO();
        passengerAdminInsertDTO.setUserId(1L);
        passengerAdminInsertDTO.setFirstName("DARIUS");
        passengerAdminInsertDTO.setLastName("DARIUS");
        passengerAdminInsertDTO.setAge(20);
        passengerAdminInsertDTO.setGender("M");
        passengerAdminInsertDTO.setPhoneNumber("1234567890");

        when(userService.getUserById(passengerAdminInsertDTO.getUserId())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> passengerService.addPassenger(passengerAdminInsertDTO));

        verify(passengerRepository, times(0)).save(any(Passenger.class));
    }

    @Test
    @DisplayName("Should update passenger")
    void shouldUpdatePassenger() throws EntityNotFoundException {
        Passenger passenger = PassengerBuilder.random().build();

        PassengerAdminUpdateDTO passengerAdminUpdateDTO = new PassengerAdminUpdateDTO();
        passengerAdminUpdateDTO.setFirstName(passenger.getPassengerDetails().getFirstName());
        passengerAdminUpdateDTO.setLastName(passenger.getPassengerDetails().getLastName());
        passengerAdminUpdateDTO.setAge(passenger.getPassengerDetails().getAge());
        passengerAdminUpdateDTO.setGender(passenger.getPassengerDetails().getGender());
        passengerAdminUpdateDTO.setPhoneNumber(passenger.getPassengerDetails().getPhoneNumber());
        passengerAdminUpdateDTO.setId(passenger.getId());
        passengerAdminUpdateDTO.setUserId(passenger.getUserId());

        PassengerResponseDTO passengerResponseDTO = new PassengerResponseDTO();

        passengerResponseDTO.setId(passenger.getId());
        passengerResponseDTO.setUserId(passenger.getUserId());
        passengerResponseDTO.setFirstName(passenger.getPassengerDetails().getFirstName());
        passengerResponseDTO.setLastName(passenger.getPassengerDetails().getLastName());
        passengerResponseDTO.setAge(passenger.getPassengerDetails().getAge());
        passengerResponseDTO.setGender(passenger.getPassengerDetails().getGender());
        passengerResponseDTO.setPhoneNumber(passenger.getPassengerDetails().getPhoneNumber());

        when(passengerRepository.findById(passengerAdminUpdateDTO.getId())).thenReturn(Optional.of(passenger));
        when(passengerRepository.save(any(Passenger.class))).thenReturn(passenger);

        Optional<PassengerResponseDTO> passengerResponseDTOResult = passengerService.updatePassenger(passengerAdminUpdateDTO);

        assertTrue(passengerResponseDTOResult.isPresent());
        assertEquals(passengerResponseDTO, passengerResponseDTOResult.get());

        verify(passengerRepository, times(1)).findById(passengerAdminUpdateDTO.getId());
        verify(passengerRepository, times(1)).save(any(Passenger.class));
    }

    @Test
    @DisplayName("updatePassenger should throw EntityNotFoundException when user not found")
    void updatePassengerShouldThrowEntityNotFoundExceptionWhenUserNotFound(){
        PassengerAdminUpdateDTO passengerAdminUpdateDTO = new PassengerAdminUpdateDTO();
        passengerAdminUpdateDTO.setId(1L);
        passengerAdminUpdateDTO.setUserId(1L);
        passengerAdminUpdateDTO.setFirstName("DARIUS");
        passengerAdminUpdateDTO.setLastName("DARIUS");
        passengerAdminUpdateDTO.setAge(20);
        passengerAdminUpdateDTO.setGender("M");
        passengerAdminUpdateDTO.setPhoneNumber("1234567890");

        when(passengerRepository.findById(passengerAdminUpdateDTO.getId())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> passengerService.updatePassenger(passengerAdminUpdateDTO));

        verify(passengerRepository, times(0)).save(any(Passenger.class));
    }

    @Test
    @DisplayName("Should get passenger by id")
    void shouldGetPassengerById() throws EntityNotFoundException {
        Passenger passenger = PassengerBuilder.random().build();
        when(passengerRepository.findById(passenger.getId())).thenReturn(Optional.of(passenger));

        Optional<PassengerResponseDTO> passengerResponseDTOResult = passengerService.getPassengerById(passenger.getId());

        assertTrue(passengerResponseDTOResult.isPresent());
        assertEquals(passenger.getId(), passengerResponseDTOResult.get().getId());

        verify(passengerRepository, times(1)).findById(passenger.getId());
    }

    @Test
    @DisplayName("getPassengerById should throw EntityNotFoundException when user not found")
    void getPassengerByIdShouldThrowEntityNotFoundExceptionWhenUserNotFound(){
        when(passengerRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> passengerService.getPassengerById(1L));

        verify(passengerRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Should get passenger by user id")
    void shouldGetPassengerByUserId() throws EntityNotFoundException {
        Passenger passenger = PassengerBuilder.random().build();
        when(passengerRepository.findByUserId(passenger.getUserId())).thenReturn(Optional.of(passenger));

        Optional<PassengerResponseDTO> passengerResponseDTOResult = passengerService.getPassengerByUserId(passenger.getUserId());

        assertTrue(passengerResponseDTOResult.isPresent());
        assertEquals(passenger.getId(), passengerResponseDTOResult.get().getId());

        verify(passengerRepository, times(1)).findByUserId(passenger.getUserId());
    }

    @Test
    @DisplayName("getPassengerByUserId should throw EntityNotFoundException when user not found")
    void getPassengerByUserIdShouldThrowEntityNotFoundExceptionWhenUserNotFound(){
        when(passengerRepository.findByUserId(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> passengerService.getPassengerByUserId(1L));

        verify(passengerRepository, times(1)).findByUserId(1L);
    }
}