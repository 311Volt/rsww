package com.yetistudios.rsww.messages.event;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PlaneReservationFailedEvent {
    public String reservationId;
    public String reason;
}
