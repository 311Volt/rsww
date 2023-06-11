package com.yetistudios.rsww.touroperator.cmd.exception;

public class OfferAmountIsInsufficientException extends  RuntimeException{
    public String reservationId;

    public OfferAmountIsInsufficientException() {
        super();
    }
    public OfferAmountIsInsufficientException(String message) {
        super(message);
    }
    public OfferAmountIsInsufficientException(String message, String reservationSourceId) {
        super(message);
        this.reservationId = reservationSourceId;
    }
}
