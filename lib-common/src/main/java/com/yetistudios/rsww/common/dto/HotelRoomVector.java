package com.yetistudios.rsww.common.dto;

import lombok.Builder;

@Builder
public class HotelRoomVector implements Comparable<HotelRoomVector> {
    public int numSingleRooms;
    public int numDoubleRooms;
    public int numTripleRooms;

    public HotelRoomVector(int numSingleRooms, int numDoubleRooms, int numTripleRooms) {
        this.numSingleRooms = numSingleRooms;
        this.numDoubleRooms = numDoubleRooms;
        this.numTripleRooms = numTripleRooms;
    }

    public void max(HotelRoomVector other) {
        numSingleRooms = Math.max(numSingleRooms, other.numSingleRooms);
        numDoubleRooms = Math.max(numDoubleRooms, other.numDoubleRooms);
        numTripleRooms = Math.max(numTripleRooms, other.numTripleRooms);
    }

    public int population() {
        return numSingleRooms + numDoubleRooms + numTripleRooms;
    }

    public boolean isNonZero() {
        return numSingleRooms>0 || numDoubleRooms>0 || numTripleRooms>0;
    }

    public static HotelRoomVector minus(HotelRoomVector lhs, HotelRoomVector rhs) {
        return new HotelRoomVector(
                lhs.numSingleRooms - rhs.numSingleRooms,
                lhs.numDoubleRooms - rhs.numDoubleRooms,
                lhs.numTripleRooms - rhs.numTripleRooms);
    }

    public static HotelRoomVector plus(HotelRoomVector lhs, HotelRoomVector rhs) {
        return new HotelRoomVector(
                lhs.numSingleRooms - rhs.numSingleRooms,
                lhs.numDoubleRooms - rhs.numDoubleRooms,
                lhs.numTripleRooms - rhs.numTripleRooms);
    }

    public static HotelRoomVector max(HotelRoomVector lhs, HotelRoomVector rhs) {
        return new HotelRoomVector(
                Integer.max(lhs.numSingleRooms, rhs.numSingleRooms),
                Integer.max(lhs.numDoubleRooms, rhs.numDoubleRooms),
                Integer.max(lhs.numTripleRooms, rhs.numTripleRooms)
        );
    }


    public static HotelRoomVector min(HotelRoomVector lhs, HotelRoomVector rhs) {
        return new HotelRoomVector(
                Integer.min(lhs.numSingleRooms, rhs.numSingleRooms),
                Integer.min(lhs.numDoubleRooms, rhs.numDoubleRooms),
                Integer.min(lhs.numTripleRooms, rhs.numTripleRooms)
        );
    }


    @Override
    public int compareTo(HotelRoomVector o) {
        int c1 = Integer.compare(numSingleRooms, o.numSingleRooms);
        int c2 = Integer.compare(numDoubleRooms, o.numDoubleRooms);
        int c3 = Integer.compare(numTripleRooms, o.numTripleRooms);
        if(c1 > 0 || c2 > 0 || c3 > 0) {
            return 1;
        } else if(c1 == 0 && c2 == 0 && c3 == 0) {
            return 0;
        }
        return -1;
    }

    @Override
    public String toString() {
        return String.format("({},{},{})", numSingleRooms, numDoubleRooms, numTripleRooms);
    }

    public boolean fitsInto(HotelRoomVector vec) {
        return compareTo(vec) < 0;
    }
}
