package com.yetistudios.rsww.common.dto;

public class HotelAgeRange {
    public int lowerBound;
    public int upperBound;
    public double pricePerNight;

    public boolean containsAge(int age) {
        return age >= lowerBound && age < upperBound;
    }
}
