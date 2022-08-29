package org.darius.mapper;

import org.darius.dto.request.insert.UserRegisterDTO;
import org.darius.dto.request.update.UserUpdateDTO;
import org.darius.dto.response.UserUpdateResponseDTO;
import org.darius.entity.User;

public class UserMapper {
    public static User convertUserRegisterDtoToUser(UserRegisterDTO userRegisterDTO) {
        if (userRegisterDTO != null) {
            User user = new User();
            user.setUsername(userRegisterDTO.getUsername());
            user.setPassword(userRegisterDTO.getPassword());
            return user;
        }
        return null;
    }

    public static User updateUserFromUserUpdateDTO(UserUpdateDTO userUpdateDTO, User user) {
        if (userUpdateDTO != null) {
            user.setPassword(userUpdateDTO.getPassword());
            return user;
        }
        return null;
    }

    public static UserUpdateResponseDTO convertUserToUserUpdateResponseDTO(User user) {
        if (user != null) {
            UserUpdateResponseDTO userUpdateResponseDTO = new UserUpdateResponseDTO();
            userUpdateResponseDTO.setUsername(user.getUsername());
            return userUpdateResponseDTO;
        }
        return null;
    }
}
