package com.yetistudios.rsww.rswwhotel.query.entity;

public class HotelAgeRange {
    public int lowerBound;
    public int upperBound;
    public double pricePerNight;

    public boolean containsAge(int age) {
        return age >= lowerBound && age < upperBound;
    }
}
