package com.yetistudios.rsww.touroperator.cmd.exception;

public class OfferDoesNotExistException extends RuntimeException {
    public OfferDoesNotExistException() {
        super();
    }
    public OfferDoesNotExistException(String message) {
        super(message);
    }
}
