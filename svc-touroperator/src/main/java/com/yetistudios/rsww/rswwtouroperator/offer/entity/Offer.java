package com.yetistudios.rsww.rswwtouroperator.offer.entity;

import lombok.Builder;

@Builder
public class Offer {
    public final String id;
    public final String departureFlightNumber;
    public final String returnFlightNumber;
    public final String hotelCode;
    public final String firstNightDate;
    public final int numSingleRooms;
    public final int numDoubleRooms;
    public final int numTripleRooms;
    public final int durationDays;
}
