package com.yetistudios.rsww.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HotelDocument {

    public String code;
    public String name;
    public double standard;
    public double latitude;
    public double longitude;
    public String airportCode;
    public String country;
    public Integer numSingleRooms;
    public Integer numDoubleRooms;
    public Integer numTripleRooms;
    public HotelAgeRange ageRange0;
    public HotelAgeRange ageRange1;
    public HotelAgeRange ageRange2;

}
