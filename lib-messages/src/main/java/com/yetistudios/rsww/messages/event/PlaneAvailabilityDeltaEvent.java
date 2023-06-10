package com.yetistudios.rsww.messages.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlaneAvailabilityDeltaEvent {
    public Integer flightNumber;
    public Integer deltaSeats;
}
