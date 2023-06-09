package com.yetistudios.rsww.rswwflight.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class FlightAvailability {
    @Id
    public Integer flightNumber;
    public Integer numTakenSeats;

    public static FlightAvailability ofSnapshot(FlightAvailabilitySnapshot snapshot) {
        return FlightAvailability.builder()
                .flightNumber(snapshot.id.flightNumber)
                .numTakenSeats(snapshot.numTakenSeats)
                .build();
    }


    public void consumeEvent(FlightAvailabilityDeltaEventEntity event) {
        numTakenSeats += event.deltaSeats;
    }

    public void consumeEvents(List<FlightAvailabilityDeltaEventEntity> events) {
        events.forEach(this::consumeEvent);
    }
}
