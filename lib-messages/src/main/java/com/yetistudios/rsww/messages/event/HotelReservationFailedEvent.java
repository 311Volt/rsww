package com.yetistudios.rsww.messages.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HotelReservationFailedEvent {
    public String reservationId;
    public String reason;
}
