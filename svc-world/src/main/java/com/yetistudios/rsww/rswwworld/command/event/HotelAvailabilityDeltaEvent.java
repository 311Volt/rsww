package com.yetistudios.rsww.rswwworld.command.event;

import jakarta.persistence.*;

@Entity
@Table(name = "hotelevents", indexes = @Index(columnList = "timestamp"))
public class HotelAvailabilityDeltaEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public Long id;
    public long timestamp;
    public int deltaSingleRooms;
    public int deltaDoubleRooms;
    public int deltaTripleRooms;
}
