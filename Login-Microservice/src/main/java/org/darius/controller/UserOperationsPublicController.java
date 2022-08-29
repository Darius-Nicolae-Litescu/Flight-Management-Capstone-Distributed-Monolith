package org.darius.controller;

import org.darius.configuration.jwt.JwtUtils;
import org.darius.dto.request.insert.UserRegisterDTO;
import org.darius.dto.request.validate.UserLoginDTO;
import org.darius.dto.response.UserJWTResponse;
import org.darius.dto.response.UserRegistrationResponse;
import org.darius.exception.RoleCouldNotBeAssignedException;
import org.darius.exception.UserCouldNotBeCreatedException;
import org.darius.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user/public")
public class UserOperationsPublicController {
    private final  Logger logger = LoggerFactory.getLogger(UserOperationsPublicController.class);
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<UserJWTResponse> doLogin(@RequestBody @Valid UserLoginDTO userEntry) throws Exception {
        logger.info("Trying to authenticate username: {}", userEntry.getUsername());
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userEntry.getUsername(), userEntry.getPassword()));
        } catch (Exception e) {
            logger.info("User with username authentication failed: {}", userEntry.getUsername());
            throw new Exception("Bad credentials ");
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(userEntry.getUsername());

        String token = jwtUtils.generateToken(userDetails);

        List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        UserJWTResponse userJWTResponse = new UserJWTResponse(userEntry.getUsername(), token, roles);

        return new ResponseEntity<>(userJWTResponse, HttpStatus.OK);
    }

    @PostMapping("/register")
    public UserRegistrationResponse doRegister(@RequestBody @Valid UserRegisterDTO userRegisterDTO) throws UserCouldNotBeCreatedException, RoleCouldNotBeAssignedException {
        logger.info("Trying to register user with username: {}", userRegisterDTO.getUsername());

        return userService.registerUser(userRegisterDTO);
    }

    private String getUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}