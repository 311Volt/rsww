package com.yetistudios.rsww.rswwflight.entity;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Embeddable
@AttributeOverrides({
        @AttributeOverride( name = "flightNumber", column = @Column(name = "flight_number")),
        @AttributeOverride( name = "timestamp", column = @Column(name = "timestamp"))
})
@Data
@Builder
@AllArgsConstructor
public class FlightNumAndTimestampId {
    public Integer flightNumber;
    public Timestamp timestamp;
}
