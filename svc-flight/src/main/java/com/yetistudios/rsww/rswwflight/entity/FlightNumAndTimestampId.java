package com.yetistudios.rsww.rswwflight.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Embeddable
@Data
@Builder
@AllArgsConstructor
public class FlightNumAndTimestampId {
    public Integer flightNumber;
    public Timestamp timestamp;
}
