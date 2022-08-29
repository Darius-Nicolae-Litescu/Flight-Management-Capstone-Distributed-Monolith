package org.darius.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
public class UserCouldNotBeCreatedException extends Exception {
    private String username;

    public UserCouldNotBeCreatedException(String username) {
        this.username = username;
    }

    public UserCouldNotBeCreatedException(String message, String username) {
        super(message);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
