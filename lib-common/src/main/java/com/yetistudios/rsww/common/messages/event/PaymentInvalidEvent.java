package com.yetistudios.rsww.messages.event;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaymentInvalidEvent {
    private String reservationId;
}
