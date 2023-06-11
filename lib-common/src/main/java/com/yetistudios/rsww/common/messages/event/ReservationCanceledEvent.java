package com.yetistudios.rsww.common.messages.event;

import lombok.Data;

@Data
public class ReservationCanceledEvent {
    private String reservationId;
}
