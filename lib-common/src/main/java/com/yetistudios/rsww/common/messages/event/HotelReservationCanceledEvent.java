package com.yetistudios.rsww.messages.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class HotelReservationCanceledEvent {
    public String reservationId;
}
