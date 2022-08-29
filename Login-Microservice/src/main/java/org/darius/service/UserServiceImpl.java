package org.darius.service;


import org.darius.dto.request.insert.UserRegisterDTO;
import org.darius.dto.request.update.UserUpdateDTO;
import org.darius.dto.response.UserRegistrationResponse;
import org.darius.dto.response.UserUpdateResponseDTO;
import org.darius.entity.Role;
import org.darius.entity.User;
import org.darius.exception.RoleCouldNotBeAssignedException;
import org.darius.exception.UserCouldNotBeCreatedException;
import org.darius.mapper.UserMapper;
import org.darius.repository.RoleRepository;
import org.darius.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    public static final String ROLE_USER = "ROLE_USER";
    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional
    public UserRegistrationResponse registerUser(UserRegisterDTO userRegisterDTO) throws UserCouldNotBeCreatedException, RoleCouldNotBeAssignedException {
        logger.info("Registering user");
        User user = UserMapper.convertUserRegisterDtoToUser(userRegisterDTO);
        if (user != null) {
            userRepository.save(user);
            addRoleToUser(ROLE_USER, userRegisterDTO.getUsername());
            return new UserRegistrationResponse(user.getUsername(), "User created successfully");
        } else {
            throw new UserCouldNotBeCreatedException("Could not create user", userRegisterDTO.getUsername());
        }
    }

    @Override
    public void addRoleToUser(String roleName, String username) throws RoleCouldNotBeAssignedException {
        logger.info("Adding role to user");
        Role role = roleRepository.getRoleByRoleName(roleName);
        Optional<User> userOptional = userRepository.findUserByUsername(username);
        if (role == null || !userOptional.isPresent()) {
            throw new RoleCouldNotBeAssignedException("Could not assign role:" + roleName + " to username:" + username);
        }
        User user = userOptional.get();
        if (user.getRoles() == null) {
            user.setRoles(new HashSet<>());
        }
        user.getRoles().add(role);
        userRepository.save(user);
    }

    @Override
    public User getUserEntityByUsername(String username) {
        logger.info("Getting user entity by username");
        return userRepository.findUserByUsername(username).orElse(null);
    }

    @Override
    public Optional<User> getUserEntityById(Long id) {
        logger.info("Getting user entity by id");
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        logger.info("Getting user by username");
        return this.userRepository.findUserByUsername(username);
    }

    @Override
    public Set<Role> addRoles(Set<Role> roles) {
        logger.info("Adding roles");
        return new HashSet<>(this.roleRepository.saveAll(roles));
    }

    @Override
    public Role getRoleByName(String roleName) {
        logger.info("Getting role by name");
        return this.roleRepository.getRoleByRoleName(roleName);
    }

    @Override
    public Optional<UserUpdateResponseDTO> updateUser(String username, UserUpdateDTO userUpdateDTO) {
        logger.info("Updating user");
        User user = this.userRepository.findUserByUsername(username).orElse(null);
        if (user == null) {
            return Optional.empty();
        }
        UserMapper.updateUserFromUserUpdateDTO(userUpdateDTO, user);
        this.userRepository.save(user);
        return Optional.of(UserMapper.convertUserToUserUpdateResponseDTO(user));
    }

    @Override
    public List<User> getAll() {
        logger.info("Getting all users");
        return userRepository.findAll();
    }

    @Override
    public List<Long> getAllIds() {
        logger.info("Getting all users ids");
        return userRepository.findAllIds();
    }

    @Override
    public Long roleCount() {
        logger.info("Getting role count");
        return roleRepository.count();
    }

    @Override
    public User insertUser(User user) {
        logger.info("Inserting user");
        User savedUser = userRepository.save(user);
        return savedUser;
    }

    @Override
    public Long userCount() {
        logger.info("Getting user count");
        return userRepository.count();
    }

    @Override
    public void insertAll(List<User> usersToInsert) {
        logger.info("Inserting all users");
        userRepository.saveAll(usersToInsert);
    }
}
