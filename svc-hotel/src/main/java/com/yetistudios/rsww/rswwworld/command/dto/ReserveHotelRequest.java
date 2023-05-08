package com.yetistudios.rsww.rswwworld.command.dto;

import com.yetistudios.rsww.rswwworld.query.entity.HotelOccupationVector;

public class ReserveHotelRequest {
    public String hotelCode;
    public long timestampBegin;
    public long timestampEnd;
    public int numSingleRooms;
    public int numDoubleRooms;
    public int numTripleRooms;

    public HotelOccupationVector getOoccupationVector() {
        return new HotelOccupationVector(numSingleRooms, numDoubleRooms, numTripleRooms);
    }
}
