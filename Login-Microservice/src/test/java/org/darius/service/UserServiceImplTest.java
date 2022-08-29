package org.darius.service;

import org.darius.dto.request.insert.UserRegisterDTO;
import org.darius.dto.request.update.UserUpdateDTO;
import org.darius.dto.response.UserRegistrationResponse;
import org.darius.dto.response.UserUpdateResponseDTO;
import org.darius.entity.Role;
import org.darius.entity.User;
import org.darius.exception.RoleCouldNotBeAssignedException;
import org.darius.exception.UserCouldNotBeCreatedException;
import org.darius.repository.RoleRepository;
import org.darius.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("The register user method should insert the entity")
    void shouldRegisterUser() throws RoleCouldNotBeAssignedException, UserCouldNotBeCreatedException {
        UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
        userRegisterDTO.setUsername("username");
        userRegisterDTO.setPassword("password");

        User user = new User();
        user.setUsername("username");
        user.setPassword("password");

        String roleName = "ROLE_USER";

        when(roleRepository.getRoleByRoleName(roleName)).thenReturn(new Role(1L ,roleName));
        when(userRepository.findUserByUsername(user.getUsername())).thenReturn(Optional.of(user));

        when(userRepository.save(user)).thenReturn(user);
        UserRegistrationResponse userResponse = userService.registerUser(userRegisterDTO);
        assertEquals(user.getUsername(), userResponse.getUsername());

        verify(userRepository).save(user);
        verify(roleRepository).getRoleByRoleName(roleName);
        verify(userRepository).findUserByUsername(user.getUsername());

    }

    @Test
    @DisplayName("The register user method should throw an exception when the role could not be assigned")
    void shouldThrowExceptionWhenRoleCouldNotBeAssigned() throws RoleCouldNotBeAssignedException, UserCouldNotBeCreatedException {
        UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
        userRegisterDTO.setUsername("username");
        userRegisterDTO.setPassword("password");

        User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        when(userRepository.save(user)).thenReturn(user);
        assertThrows(RoleCouldNotBeAssignedException.class, () -> userService.registerUser(userRegisterDTO));

        verify(userRepository).save(user);
    }

    @Test
    @DisplayName("The method should return the entity by username")
    void shouldGetUserEntityByUsername() {
        String username = "username";
        User user = new User();
        user.setUsername(username);
        when(userRepository.findUserByUsername(username)).thenReturn(Optional.of(user));
        User userResponse = userService.getUserEntityByUsername(username);
        assertEquals(user.getUsername(), userResponse.getUsername());

        verify(userRepository).findUserByUsername(username);
    }

    @Test
    @DisplayName("The method should return the entity by id")
    void shouldGetUserEntityById() {
        Long id = 1L;
        User user = new User();
        user.setUserId(id);
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        User userResponse = userService.getUserEntityById(id).get();
        assertEquals(user.getUserId(), userResponse.getUserId());

        verify(userRepository).findById(id);
    }

    @Test
    @DisplayName("The method should return the entity by username")
    void shouldGetUserByUsername() {
        String username = "username";
        User user = new User();
        user.setUsername(username);
        when(userRepository.findUserByUsername(username)).thenReturn(Optional.of(user));
        Optional<User> userResponse = userService.getUserByUsername(username);
        assertTrue(userResponse.isPresent());
        assertEquals(user.getUsername(), userResponse.get().getUsername());

        verify(userRepository).findUserByUsername(username);
    }

    @Test
    @DisplayName("The method should add the roles in db")
    void shouldAddRoles() {
        String roleName = "ROLE_User";
        Role role = new Role(1L, roleName);
        Role role2 = new Role(2L, roleName + 1);
        List<Role> roles = new ArrayList<>();
        roles.add(role);
        roles.add(role2);

        when(roleRepository.saveAll(any())).thenReturn(roles);
        Set<Role> rolesResponse = userService.addRoles(new HashSet<>(roles));
        assertEquals(roles.size(), rolesResponse.size());

        verify(roleRepository).saveAll(any());
    }

    @Test
    @DisplayName("The method should return the role by name")
    void shouldGetRoleByName() {
        String roleName = "ROLE_User";
        Role role = new Role(1L, roleName);
        when(roleRepository.getRoleByRoleName(roleName)).thenReturn(role);
        Role roleResponse = userService.getRoleByName(roleName);
        assertEquals(role.getRoleName(), roleResponse.getRoleName());

        verify(roleRepository).getRoleByRoleName(roleName);
    }

    @Test
    @DisplayName("The method should update the user")
    void shouldUpdateUser() {
        UserUpdateDTO userUpdateDTO = new UserUpdateDTO();
        userUpdateDTO.setPassword("password");

        User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        when(userRepository.findUserByUsername(user.getUsername())).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);
        Optional<UserUpdateResponseDTO> userResponse = userService.updateUser(user.getUsername(), userUpdateDTO);

        assertTrue(userResponse.isPresent());
        assertEquals(user.getUsername(), userResponse.get().getUsername());

        verify(userRepository).findUserByUsername(user.getUsername());
        verify(userRepository).save(user);
    }

    @Test
    @DisplayName("The method should return all users")
    void shouldGetAll() {
        User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        List<User> users = new ArrayList<>();
        users.add(user);
        when(userRepository.findAll()).thenReturn(users);
        List<User> usersResponse = userService.getAll();
        assertEquals(users.size(), usersResponse.size());

        verify(userRepository).findAll();
    }

    @Test
    @DisplayName("The method should return all user ids")
    void shouldGetAllIds() {
        User user = new User();
        user.setUserId(1L);
        user.setUsername("username");
        user.setPassword("password");
        List<User> users = new ArrayList<>();
        users.add(user);
        when(userRepository.findAllIds()).thenReturn(Arrays.asList(user.getUserId()));
        List<Long> usersResponse = userService.getAllIds();
        assertEquals(users.size(), usersResponse.size());

        verify(userRepository).findAllIds();
    }

    @Test
    @DisplayName("The method should return roles count")
    void roleCount() {
        Long count = 1L;
        when(roleRepository.count()).thenReturn(1L);
        Long rolesCount = userService.roleCount();
        assertEquals(count, rolesCount);

        verify(roleRepository).count();
    }

}