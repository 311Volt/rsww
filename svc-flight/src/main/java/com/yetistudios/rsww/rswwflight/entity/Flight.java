package com.yetistudios.rsww.rswwflight.entity;

import com.yetistudios.rsww.messages.misc.DepartureOrArrivalDocument;
import com.yetistudios.rsww.messages.misc.FlightDocument;
import com.yetistudios.rsww.rswwflight.util.DatetimeUtil;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Builder;

@Entity
@Builder
@Table(
        name = "flights",
        indexes = {
        @Index(columnList = "departureTimestamp"),
        @Index(columnList = "arrivalTimestamp")
})
public class Flight {
    @Id
    public Integer flightNumber;
    public Integer numSeats;
    public Boolean isReturn;
    public String departureAirportCode;
    public String departureDate;
    public String departureTime;
    public Long departureTimestamp;
    public String arrivalAirportCode;
    public String arrivalDate;
    public String arrivalTime;
    public Long arrivalTimestamp;

    public static Flight ofSourceDocument(FlightDocument document) {
        return Flight.builder()
                .flightNumber(document.flightNumber)
                .numSeats(document.numSeats)
                .isReturn(document.isReturn)
                .departureAirportCode(document.departure.airportCode)
                .departureDate(document.departure.date)
                .departureTime(document.departure.time)
                .departureTimestamp(DatetimeUtil.dateAndTimeToUnix(document.departure.date, document.departure.time))
                .arrivalAirportCode(document.arrival.airportCode)
                .arrivalDate(document.arrival.date)
                .arrivalTime(document.arrival.time)
                .arrivalTimestamp(DatetimeUtil.dateAndTimeToUnix(document.arrival.date, document.arrival.time))
                .build();
    }

    public FlightDocument toDocument() {
        return FlightDocument.builder()
                .flightNumber(flightNumber)
                .numSeats(numSeats)
                .isReturn(isReturn)
                .departure(DepartureOrArrivalDocument.builder()
                        .airportCode(departureAirportCode)
                        .date(departureDate)
                        .time(departureTime)
                        .build())
                .arrival(DepartureOrArrivalDocument.builder()
                        .airportCode(arrivalAirportCode)
                        .date(arrivalDate)
                        .time(arrivalTime)
                        .build())
                .build();
    }
}
