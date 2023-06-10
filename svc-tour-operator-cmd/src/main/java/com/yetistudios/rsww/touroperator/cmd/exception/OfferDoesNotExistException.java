package com.yetistudios.rsww.touroperator.cmd.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class OfferDoesNotExistException extends RuntimeException {
    public OfferDoesNotExistException() {
        super();
    }
    public OfferDoesNotExistException(String message) {
        super(message);
    }
}
