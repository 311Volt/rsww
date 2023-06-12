package com.yetistudios.rsww.common.messages.command;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ValidatePaymentCommand {
    private String reservationId;
    private double price;
    private String clientId;
    private boolean paid;
}
