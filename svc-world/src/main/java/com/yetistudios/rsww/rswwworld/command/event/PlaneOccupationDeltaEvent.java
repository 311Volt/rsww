package com.yetistudios.rsww.rswwworld.command.event;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("plane-events")
public class PlaneOccupationDeltaEvent {

    @Id
    public ObjectId _id;
    @Indexed
    public long timestamp;
    @Indexed
    public int flightNumber;
    public int deltaSeats;
}
