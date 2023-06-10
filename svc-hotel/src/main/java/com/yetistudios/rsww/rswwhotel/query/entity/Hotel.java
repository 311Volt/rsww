package com.yetistudios.rsww.rswwhotel.query.entity;

import com.yetistudios.rsww.common.dto.HotelSummary;
import lombok.Builder;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("hotels")
@Builder
public class Hotel {
    public ObjectId _id;
    @Indexed
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

    public HotelSummary toSummary() {
        return HotelSummary.builder()
                .code(code)
                .airportCode(airportCode)
                .country(country)
                .latitude(latitude)
                .longitude(longitude)
                .name(name)
                .standard(standard)
                .build();
    }
}
