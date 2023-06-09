package com.yetistudios.rsww.rswwflight.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Builder
@Table(name = "flight_availability")
public class FlightAvailability {
    @Id
    public Integer flightNumber;
    public Integer numTakenSeats;
}
