package com.yetistudios.rsww.rswwworld.query.entity;

import lombok.Builder;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("airports")
@Builder
public class Airport {
    public ObjectId _id;
    public String code;
    public String name;
    public boolean forDeparture;
}
