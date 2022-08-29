package org.darius.mapper;

import org.darius.dto.request.insert.UserRegisterDTO;
import org.darius.model.Passenger;
import org.darius.model.PassengerDetails;
import org.darius.model.User;

public class UserMapper {
    public final static User convertUserRegisterDtoToUser(UserRegisterDTO userRegisterDTO) {
        if (userRegisterDTO != null) {
            User user = new User();
            user.setUsername(userRegisterDTO.getUsername());
            user.setPassword(userRegisterDTO.getPassword());
            user.setPassenger(new Passenger());
            user.getPassenger().setPassengerDetails(new PassengerDetails());
            user.getPassenger().getPassengerDetails().setFirstName(userRegisterDTO.getFirstName());
            user.getPassenger().getPassengerDetails().setLastName(userRegisterDTO.getLastName());
            user.getPassenger().getPassengerDetails().setAge(userRegisterDTO.getAge());
            user.getPassenger().getPassengerDetails().setPhoneNumber(userRegisterDTO.getPhoneNumber());
            user.getPassenger().getPassengerDetails().setGender(userRegisterDTO.getGender());
            return user;
        }
        return null;
    }
}
