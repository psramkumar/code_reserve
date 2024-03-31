package com.b2b.code.Exception;

import com.b2b.code.Exception.ApplicationExceptions.DeviceAlreadyBlockedException;
import com.b2b.code.Exception.ApplicationExceptions.DeviceNeverBlockedException;
import com.b2b.code.Exception.ApplicationExceptions.DeviceNotAvailableException;
import com.b2b.code.Exception.ApplicationExceptions.UnauthorizedException;
import com.b2b.code.resp.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.MissingRequestValueException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = DeviceNotAvailableException.class)
    public ResponseEntity<ErrorResponse> handleException(DeviceNotAvailableException e) {
        return error(HttpStatus.NOT_FOUND, "DATA_NOT_FOUND", e.getMessage());
    }

    @ExceptionHandler(value = {DeviceAlreadyBlockedException.class})
    public ResponseEntity<ErrorResponse> handleApplicationException(DeviceAlreadyBlockedException e) {
        return error(HttpStatus.BAD_REQUEST, "INVALID_REQUEST_EXCEPTION", e.getMessage());
    }

    @ExceptionHandler(value = {DeviceNeverBlockedException.class})
    public ResponseEntity<ErrorResponse> handleApplicationException(DeviceNeverBlockedException e) {
        return error(HttpStatus.BAD_REQUEST, "INVALID_REQUEST_EXCEPTION", e.getMessage());
    }

    @ExceptionHandler(value = {MissingRequestValueException.class})
    public ResponseEntity<ErrorResponse> handleException(MissingRequestValueException e) {
        return error(HttpStatus.BAD_REQUEST, "INVALID_REQUEST_EXCEPTION", e.getReason());
    }

    @ExceptionHandler(value = {UnauthorizedException.class})
    public ResponseEntity<ErrorResponse> handleException(UnauthorizedException e) {
        return error(HttpStatus.BAD_REQUEST, "UNAUTHORIZED", e.getMessage());
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        e.printStackTrace();
        return error(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", e.getMessage());
    }

    private ResponseEntity<ErrorResponse> error(HttpStatus httpStatus, String errorCode, String errorMessage) {
        return new ResponseEntity<>(new ErrorResponse(errorMessage, errorCode), httpStatus);
    }
}
