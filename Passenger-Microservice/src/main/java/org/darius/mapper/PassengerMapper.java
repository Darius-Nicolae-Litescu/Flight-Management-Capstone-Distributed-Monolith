package org.darius.mapper;

import org.darius.dto.request.PassengerAdminInsertDTO;
import org.darius.dto.request.PassengerAdminUpdateDTO;
import org.darius.dto.request.PassengerUserInsertDTO;
import org.darius.dto.request.PassengerUserUpdateDTO;
import org.darius.dto.response.PassengerResponseDTO;
import org.darius.entity.Passenger;
import org.darius.entity.PassengerDetails;

public class PassengerMapper {

    public static Passenger passengerAdminInsertDtoToPassenger(PassengerAdminInsertDTO passengerAdminInsertDTO){
        if(passengerAdminInsertDTO == null){
            return null;
        }
        Passenger passenger = new Passenger();
        passenger.setUserId(passengerAdminInsertDTO.getUserId());
        passenger.setPassengerDetails(new PassengerDetails());
        passenger.getPassengerDetails().setFirstName(passengerAdminInsertDTO.getFirstName());
        passenger.getPassengerDetails().setLastName(passengerAdminInsertDTO.getLastName());
        passenger.getPassengerDetails().setPhoneNumber(passengerAdminInsertDTO.getPhoneNumber());
        passenger.getPassengerDetails().setAge(passengerAdminInsertDTO.getAge());
        passenger.getPassengerDetails().setGender(passengerAdminInsertDTO.getGender());
        return passenger;
    }

    public static Passenger passengerUserInsertDtoToPassenger(PassengerUserInsertDTO passengerUserInsertDTO, Long userId){
        if(passengerUserInsertDTO == null){
            return null;
        }
        Passenger passenger = new Passenger();
        passenger.setUserId(userId);
        passenger.setPassengerDetails(new PassengerDetails());
        passenger.getPassengerDetails().setFirstName(passengerUserInsertDTO.getFirstName());
        passenger.getPassengerDetails().setLastName(passengerUserInsertDTO.getLastName());
        passenger.getPassengerDetails().setPhoneNumber(passengerUserInsertDTO.getPhoneNumber());
        passenger.getPassengerDetails().setAge(passengerUserInsertDTO.getAge());
        passenger.getPassengerDetails().setGender(passengerUserInsertDTO.getGender());
        return passenger;
    }

    public static PassengerResponseDTO passengerToPassengerResponseDto(Passenger passenger){
        if(passenger == null){
            return null;
        }
        PassengerResponseDTO passengerResponseDTO = new PassengerResponseDTO();
        passengerResponseDTO.setId(passenger.getId());
        passengerResponseDTO.setUserId(passenger.getUserId());
        passengerResponseDTO.setFirstName(passenger.getPassengerDetails().getFirstName());
        passengerResponseDTO.setLastName(passenger.getPassengerDetails().getLastName());
        passengerResponseDTO.setAge(passenger.getPassengerDetails().getAge());
        passengerResponseDTO.setGender(passenger.getPassengerDetails().getGender());
        passengerResponseDTO.setPhoneNumber(passenger.getPassengerDetails().getPhoneNumber());
        return passengerResponseDTO;
    }

    public static Passenger passengerAdminUpdateDtoToPassenger(PassengerAdminUpdateDTO passengerAdminUpdateDTO, Passenger passenger){
        if(passengerAdminUpdateDTO == null){
            return null;
        }
        passenger.setUserId(passengerAdminUpdateDTO.getUserId());
        passenger.setPassengerDetails(new PassengerDetails());
        passenger.getPassengerDetails().setFirstName(passengerAdminUpdateDTO.getFirstName());
        passenger.getPassengerDetails().setLastName(passengerAdminUpdateDTO.getLastName());
        passenger.getPassengerDetails().setPhoneNumber(passengerAdminUpdateDTO.getPhoneNumber());
        passenger.getPassengerDetails().setAge(passengerAdminUpdateDTO.getAge());
        passenger.getPassengerDetails().setGender(passengerAdminUpdateDTO.getGender());
        return passenger;
    }

    public static Passenger passengerUserUpdateDtoToPassenger(PassengerUserUpdateDTO passengerUserUpdateDTO, Long userId, Passenger passenger) {
        if(passengerUserUpdateDTO == null){
            return null;
        }
        passenger.setUserId(userId);
        passenger.setPassengerDetails(new PassengerDetails());
        passenger.getPassengerDetails().setFirstName(passengerUserUpdateDTO.getFirstName());
        passenger.getPassengerDetails().setLastName(passengerUserUpdateDTO.getLastName());
        passenger.getPassengerDetails().setPhoneNumber(passengerUserUpdateDTO.getPhoneNumber());
        passenger.getPassengerDetails().setAge(passengerUserUpdateDTO.getAge());
        passenger.getPassengerDetails().setGender(passengerUserUpdateDTO.getGender());
        return passenger;
    }
}
