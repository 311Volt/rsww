package com.yetistudios.rsww.rswwworld.query.entity;

import lombok.Builder;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("flights")
@Builder
public class Flight {
    public ObjectId _id;
    public int flightNumber;
    public int numSeats;
    public FlightJuncture departure;
    public FlightJuncture arrival;
}
