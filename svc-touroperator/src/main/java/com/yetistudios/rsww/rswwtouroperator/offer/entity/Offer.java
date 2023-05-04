package com.yetistudios.rsww.rswwtouroperator.offer.entity;

import lombok.Builder;

@Builder
public class Offer {
    public final String id;
    public final String departureFlightNumber;
    public final String returnFlightNumber;
    public final String hotelCode;
    public final String firstNightDate;
    public int durationDays;
}
