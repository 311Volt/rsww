package com.yetistudios.rsww.rswwworld.entity;

import lombok.Builder;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("hotels")
@Builder
public class Hotel {
    public ObjectId _id;
    public String code;
    public String name;
    public double standard;
    public double latitude;
    public double longitude;
    public String airportCode;
    public String country;
    public String numSingleRooms;
    public String numDoubleRooms;
    public String numTripleRooms;
    public HotelAgeRange ageRange0;
    public HotelAgeRange ageRange1;
    public HotelAgeRange ageRange2;
}
