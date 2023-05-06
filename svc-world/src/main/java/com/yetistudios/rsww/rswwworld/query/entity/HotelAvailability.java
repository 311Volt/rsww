package com.yetistudios.rsww.rswwworld.query.entity;

import lombok.Builder;

@Builder
public class HotelAvailability {
    public int availableSingleRooms;
    public int availableDoubleRooms;
    public int availableTripleRooms;
}
