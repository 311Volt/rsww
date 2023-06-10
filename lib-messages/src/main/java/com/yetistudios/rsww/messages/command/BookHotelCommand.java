package com.yetistudios.rsww.messages.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookHotelCommand {
    public String reservationId;
    public String hotelCode;
    public long timestampBegin;
    public long timestampEnd;
    public int numSingleRooms;
    public int numDoubleRooms;
    public int numTripleRooms;
}
