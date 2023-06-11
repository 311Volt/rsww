package com.yetistudios.rsww.common.dto;

public class HotelAvailabilityVector extends HotelRoomVector {
    public HotelAvailabilityVector(int numSingleRooms, int numDoubleRooms, int numTripleRooms) {
        super(numSingleRooms, numDoubleRooms, numTripleRooms);
    }

    public boolean isValid() {
        return numSingleRooms > 0 && numDoubleRooms > 0 && numTripleRooms > 0;
    }

}
