package org.reallylastone.common.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public abstract class WithHttpStatusException extends RuntimeException {
    private final HttpStatus status;

    protected WithHttpStatusException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    protected WithHttpStatusException(String message, HttpStatus status, Throwable cause) {
        super(message, cause);
        this.status = status;
    }
}
