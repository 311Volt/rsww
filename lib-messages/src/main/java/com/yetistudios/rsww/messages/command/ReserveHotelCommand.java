package com.yetistudios.rsww.messages.command;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReserveHotelCommand {
    public String hotelCode;
    public long timestampBegin;
    public long timestampEnd;
    public int numSingleRooms;
    public int numDoubleRooms;
    public int numTripleRooms;
}
