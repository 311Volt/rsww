package com.yetistudios.rsww.rswwflight.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Entity
@Table(name = "flight_booking_events", indexes = {
        @Index(columnList = "reservationId")
})
public class FlightAvailabilityDeltaEventEntity {
    @EmbeddedId
    public FlightNumAndTimestampId id;
    public String reservationId;
    public Integer deltaSeats;
}
