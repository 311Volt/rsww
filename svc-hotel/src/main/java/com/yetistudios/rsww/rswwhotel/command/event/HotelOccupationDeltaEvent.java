package com.yetistudios.rsww.rswwhotel.command.event;

import lombok.Builder;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("hotel-events")
@Builder
public class HotelOccupationDeltaEvent {

    @Id
    public ObjectId _id;

    @Indexed
    public long timestamp;

    @Indexed
    public String hotelCode;
    public int deltaSingleRooms;
    public int deltaDoubleRooms;
    public int deltaTripleRooms;
}
