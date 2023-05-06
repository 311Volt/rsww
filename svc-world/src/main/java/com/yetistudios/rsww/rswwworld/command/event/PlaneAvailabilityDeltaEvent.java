package com.yetistudios.rsww.rswwworld.command.event;

import jakarta.persistence.*;

@Entity
@Table(name = "planeevents", indexes = @Index(columnList = "timestamp"))
public class PlaneAvailabilityDeltaEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public Long id;

    public long timestamp;
    public int deltaSeats;
}
