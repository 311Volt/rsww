package com.yetistudios.rsww.rswwhotel.query.entity;

import com.yetistudios.rsww.common.dto.HotelAgeRange;
import com.yetistudios.rsww.common.dto.HotelDocument;
import com.yetistudios.rsww.common.dto.HotelRoomVector;
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

    public HotelRoomVector getRoomVector() {
        return new HotelRoomVector(numSingleRooms, numDoubleRooms, numTripleRooms);
    }

    public void setRoomVector(HotelRoomVector vector) {
        numSingleRooms = vector.numSingleRooms;
        numDoubleRooms = vector.numDoubleRooms;
        numTripleRooms = vector.numTripleRooms;
    }

    public static Hotel ofDocument(HotelDocument document) {
        return Hotel.builder()
                .code(document.code)
                .name(document.name)
                .standard(document.standard)
                .latitude(document.latitude)
                .longitude(document.longitude)
                .airportCode(document.airportCode)
                .country(document.country)
                .numSingleRooms(document.numSingleRooms)
                .numDoubleRooms(document.numDoubleRooms)
                .numTripleRooms(document.numTripleRooms)
                .ageRange0(document.ageRange0)
                .ageRange1(document.ageRange1)
                .ageRange2(document.ageRange2)
                .build();
    }

    public HotelDocument toDocument() {
        return HotelDocument.builder()
                .code(code)
                .name(name)
                .standard(standard)
                .latitude(latitude)
                .longitude(longitude)
                .airportCode(airportCode)
                .country(country)
                .numSingleRooms(numSingleRooms)
                .numDoubleRooms(numDoubleRooms)
                .numTripleRooms(numTripleRooms)
                .ageRange0(ageRange0)
                .ageRange1(ageRange1)
                .ageRange2(ageRange2)
                .build();
    }
}
