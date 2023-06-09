package com.yetistudios.rsww.common.messages.command;

import com.yetistudios.rsww.common.dto.HotelRoomVector;
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

    public HotelRoomVector getVec() {
        return new HotelRoomVector(numSingleRooms, numDoubleRooms, numTripleRooms);
    }
}
