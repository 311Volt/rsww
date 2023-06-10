package com.yetistudios.rsww.messages.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookFlightCommand {
    public String reservationId;
    public int flightNumber;
    public int numSeats;
}
