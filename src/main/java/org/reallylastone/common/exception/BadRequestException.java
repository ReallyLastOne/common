package org.reallylastone.common.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends WithHttpStatusException {
    public BadRequestException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, HttpStatus.BAD_REQUEST, cause);
    }
}
