package com.yetistudios.rsww.common.messages.event;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaymentValidEvent {
    private String reservationId;
}
