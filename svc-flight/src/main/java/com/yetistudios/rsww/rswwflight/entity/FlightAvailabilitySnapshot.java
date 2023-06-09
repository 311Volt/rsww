package com.yetistudios.rsww.rswwflight.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
@Entity
@Table(name = "flight_availability_snapshots")
@Builder
public class FlightAvailabilitySnapshot {
    @EmbeddedId
    public FlightNumAndTimestampId id;

    public Integer numTakenSeats;

    public static FlightAvailabilitySnapshot empty(Integer flightNumber) {
        return FlightAvailabilitySnapshot.builder()
                .id(FlightNumAndTimestampId.builder()
                        .flightNumber(flightNumber)
                        .timestamp(Timestamp.valueOf("1970-01-01 00:00:00"))
                        .build())
                .numTakenSeats(0)
                .build();
    }

}
