package com.yetistudios.rsww.rswwworld.command.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class HotelUnavailableException extends RuntimeException {
    public HotelUnavailableException() {
        super();
    }
    public HotelUnavailableException(String message) {
        super(message);
    }
}
