package org.darius.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class RoleCouldNotBeAssignedException extends Exception {

    public RoleCouldNotBeAssignedException(String message) {
        super(message);
    }

}
