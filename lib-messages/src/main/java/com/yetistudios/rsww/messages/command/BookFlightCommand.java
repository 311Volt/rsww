package com.yetistudios.rsww.messages.command;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookFlightCommand {
    public int flightNumber;
    public int numSeats;
}
