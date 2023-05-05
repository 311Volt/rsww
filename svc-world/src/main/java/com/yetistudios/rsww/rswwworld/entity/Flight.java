package com.yetistudios.rsww.rswwworld.entity;

import lombok.Builder;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("flights")
@Builder
public class Flight {
    public ObjectId _id;
    public int flightNumber;
    public FlightJuncture departure;
    public FlightJuncture arrival;
}
