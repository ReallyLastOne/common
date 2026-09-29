package org.reallylastone.common.config;

import static org.springframework.http.HttpStatus.*;

import java.util.Arrays;

import org.reallylastone.common.exception.WithHttpStatusException;
import org.reallylastone.common.i18n.Messages;
import org.reallylastone.common.model.GenericResponse;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class GlobalControllerAdvice {
    private final Environment env;
    private final Messages messages;

    @ExceptionHandler(WithHttpStatusException.class)
    public ResponseEntity<GenericResponse<Void>> handleWithHttpStatusException(
            WithHttpStatusException ex) {
        log.error("Error", ex);

        return ResponseEntity.status(ex.getStatus())
                .body(GenericResponse.error(messages.getMessage(ex.getMessage())));
    }

    @ExceptionHandler(ObjectOptimisticLockingFailureException.class)
    public ResponseEntity<GenericResponse<Void>> handleObjectOptimisticLockingFailureException(
            ObjectOptimisticLockingFailureException ex) {
        log.error("Error", ex);

        return ResponseEntity.status(CONFLICT)
                .body(GenericResponse.error(messages.getMessage("error.conflict")));
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<GenericResponse<Void>> handleAuthenticationException(
            AuthenticationException ex) {
        log.error("Error", ex);
        return ResponseEntity.status(UNAUTHORIZED)
                .body(GenericResponse.error(messages.getMessage("error.unauthorized")));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<GenericResponse<Void>> handleAccessDeniedException(
            AccessDeniedException ex) {
        log.error("Error", ex);
        return ResponseEntity.status(FORBIDDEN)
                .body(GenericResponse.error(messages.getMessage("error.forbidden")));
    }

    @ExceptionHandler({ConstraintViolationException.class, MethodArgumentNotValidException.class})
    public ResponseEntity<GenericResponse<Void>> handleValidationException(Exception ex) {
        log.error("Error", ex);
        return ResponseEntity.status(BAD_REQUEST)
                .body(GenericResponse.error(getErrorMessage(ex.getMessage())));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<GenericResponse<Void>> handleException(Exception ex) {
        log.error("Error", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(GenericResponse.error(getErrorMessage(ex.getMessage())));
    }

    private String getErrorMessage(String exMessage) {
        return isDevMode() && exMessage != null ? exMessage : messages.getMessage("error.default");
    }

    private boolean isDevMode() {
        return Arrays.asList(env.getActiveProfiles()).contains("dev");
    }
}
