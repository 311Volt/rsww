package com.yetistudios.rsww.rswwhotel.query.entity;

public class HotelOccupationVector {
    public int takenSingleRooms;
    public int takenDoubleRooms;
    public int takenTripleRooms;

    public HotelOccupationVector(HotelOccupation occupation) {
        takenSingleRooms = occupation.getTakenSingleRooms();
        takenDoubleRooms = occupation.getTakenDoubleRooms();
        takenTripleRooms = occupation.getTakenTripleRooms();
    }

    public HotelOccupationVector(int numSingleRooms, int numDoubleRooms, int numTripleRooms) {
        takenSingleRooms = numSingleRooms;
        takenDoubleRooms = numDoubleRooms;
        takenTripleRooms = numTripleRooms;
    }

    public void max(HotelOccupationVector other) {
        takenSingleRooms = Math.max(takenSingleRooms, other.takenSingleRooms);
        takenDoubleRooms = Math.max(takenDoubleRooms, other.takenDoubleRooms);
        takenTripleRooms = Math.max(takenTripleRooms, other.takenTripleRooms);
    }
}
