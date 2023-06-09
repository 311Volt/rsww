package com.yetistudios.rsww.messages.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class PlaneReservationFailedEvent {
    public String reservationId;
    public String reason;
}
