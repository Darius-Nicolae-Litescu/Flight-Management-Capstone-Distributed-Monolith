package org.darius.service.builder;

import org.darius.model.Passenger;
import org.darius.model.Role;
import org.darius.model.User;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class UserBuilder {

    private Long userId;
    private String username;
    private String password;
    private Passenger passenger;
    private Set<Role> roles;

    public static UserBuilder defaultValues(){
        UserBuilder userBuilder = new UserBuilder();
        return new UserBuilder();
    }

    public static UserBuilder random(){
        UserBuilder userBuilder = defaultValues();
        userBuilder.userId = (long) (Math.random() * 10);
        userBuilder.username = "username";
        userBuilder.password = "password";
        userBuilder.passenger = new Passenger();
        userBuilder.roles = new HashSet<>(Arrays.asList(new Role()));
        return userBuilder;
    }

    public UserBuilder setUserId(Long userId){
        this.userId = userId;
        return this;
    }

    public UserBuilder setUsername(String username){
        this.username = username;
        return this;
    }

    public UserBuilder setPassword(String password){
        this.password = password;
        return this;
    }

    public UserBuilder setPassenger(Passenger passenger){
        this.passenger = passenger;
        return this;
    }

    public UserBuilder setRoles(Set<Role> roles){
        this.roles = roles;
        return this;
    }

    public User build(){
        return new User(userId, username, password, passenger, roles);
    }
}
