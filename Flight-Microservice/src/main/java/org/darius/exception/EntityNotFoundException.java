package org.darius.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class EntityNotFoundException extends Exception {
    private Long entityNotFoundId;

    public EntityNotFoundException(Long entityNotFoundId) {
        this.entityNotFoundId = entityNotFoundId;
    }

    public EntityNotFoundException(String message, Long entityNotFoundId) {
        super(message);
        this.entityNotFoundId = entityNotFoundId;
    }

    public Long getEntityNotFoundId() {
        return entityNotFoundId;
    }
}
