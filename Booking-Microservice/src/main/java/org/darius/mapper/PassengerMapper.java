package org.darius.mapper;


import org.darius.dto.response.UserDetailsResponseDTO;
import org.darius.entity.Booking;
import org.darius.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class PassengerMapper {
    private final Logger logger = LoggerFactory.getLogger(PassengerMapper.class);

    public static UserDetailsResponseDTO convertUserToUserDetailsResponseDto(User user, List<Booking> bookingList) {
        UserDetailsResponseDTO userDetailsResponseDTO = new UserDetailsResponseDTO();
        userDetailsResponseDTO.setUsername(user.getUsername());
        if (user.getPassenger() != null && user.getPassenger() != null) {
            userDetailsResponseDTO.setAge(user.getPassenger().getAge());
            userDetailsResponseDTO.setGender(user.getPassenger().getGender());
            userDetailsResponseDTO.setPhoneNumber(user.getPassenger().getPhoneNumber());
            userDetailsResponseDTO.setFirstName(user.getPassenger().getFirstName());
        }
        for (Booking booking : bookingList) {
            userDetailsResponseDTO.addBookingResponse(booking.getBookingId(), booking.getDateOfBooking());
        }
        return userDetailsResponseDTO;
    }
}
