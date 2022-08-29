package org.darius.service;


import org.darius.dto.request.insert.UserRegisterDTO;
import org.darius.dto.request.update.UserUpdateDTO;
import org.darius.dto.response.UserRegistrationResponse;
import org.darius.dto.response.UserUpdateResponseDTO;
import org.darius.entity.Role;
import org.darius.entity.User;
import org.darius.exception.RoleCouldNotBeAssignedException;
import org.darius.exception.UserCouldNotBeCreatedException;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserService {


    UserRegistrationResponse registerUser(UserRegisterDTO userRegisterDTO) throws UserCouldNotBeCreatedException, RoleCouldNotBeAssignedException;

    void addRoleToUser(String roleName, String username) throws RoleCouldNotBeAssignedException;

    Optional<User> getUserEntityById(Long id);

    List<User> getAll();

    List<Long> getAllIds();

    User getUserEntityByUsername(String username);

    //--------- used for initial data
    Long roleCount();

    User insertUser(User user);


    Long userCount();

    void insertAll(List<User> usersToInsert);

    Set<Role> addRoles(Set<Role> roles);

    Optional<User> getUserByUsername(String username);

    Role getRoleByName(String roleName);


    Optional<UserUpdateResponseDTO> updateUser(String username, UserUpdateDTO userUpdateDTO);
}
