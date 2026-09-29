package org.reallylastone.common.exception;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends WithHttpStatusException {
    public ResourceNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }

    public ResourceNotFoundException() {
        super("object.not.found", HttpStatus.NOT_FOUND);
    }
}
