package com.yetistudios.rsww.messages.command;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ValidatePaymentCommand {
    private String reservationId;
    private double price;
}
