package org.reallylastone.common.exception;

import org.springframework.http.HttpStatus;

public class ForbiddenException extends WithHttpStatusException {
    public ForbiddenException() {
        super("error.forbidden", HttpStatus.FORBIDDEN);
    }

    public ForbiddenException(String message) {
        super(message, HttpStatus.FORBIDDEN);
    }
}
