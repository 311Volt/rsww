package com.yetistudios.rsww.rswwflight.aggregate;

import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.UUID;

@Aggregate
public class FlightAvailabilityAggregate {
    @AggregateIdentifier
    public UUID flightAvailabilityId;
    public Integer flightNumber;
    public Integer numTakenSeats;
}
