package com.yetistudios.rsww.messages.event;

import lombok.Data;

@Data
public class ReservationCanceledEvent {
    private String reservationId;
}
