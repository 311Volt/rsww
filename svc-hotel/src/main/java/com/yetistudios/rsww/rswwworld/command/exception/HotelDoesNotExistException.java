package com.yetistudios.rsww.rswwworld.command.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class HotelDoesNotExistException extends RuntimeException {
    public HotelDoesNotExistException() {
        super();
    }
    public HotelDoesNotExistException(String message) {
        super(message);
    }
}
