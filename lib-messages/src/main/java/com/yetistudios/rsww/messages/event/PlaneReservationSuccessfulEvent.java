package com.yetistudios.rsww.messages.event;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class PlaneReservationSuccessfulEvent {
    public String reservationId;
}
