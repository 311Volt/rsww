package com.yetistudios.rsww.rswwflight.query;

import com.yetistudios.rsww.messages.misc.FlightPair;
import com.yetistudios.rsww.messages.query.CheckFlightAvailabilityQuery;
import com.yetistudios.rsww.messages.query.FindBestFlightPairQuery;
import com.yetistudios.rsww.messages.query.GetFlightBookingPriceQuery;
import com.yetistudios.rsww.rswwflight.entity.Flight;
import com.yetistudios.rsww.rswwflight.entity.FlightAvailability;
import com.yetistudios.rsww.rswwflight.repository.AirportRepository;
import com.yetistudios.rsww.rswwflight.repository.FlightRepository;
import com.yetistudios.rsww.rswwflight.service.FlightAvailabilityService;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Component
public class FlightQueryHandler {

    @Autowired
    private AirportRepository airportRepository;

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private FlightAvailabilityService flightAvailabilityService;

    private static final double PRICE_PER_SEAT_PER_HOUR = 130.0;


    @QueryHandler
    Optional<FlightPair> handle(FindBestFlightPairQuery query) {
        List<Flight> viableOutboundFlights = flightRepository.getViableOutboundFlights(query);
        List<Flight> viableReturnFlights = flightRepository.getViableReturnFlights(query);

        if(viableOutboundFlights.size() == 0 || viableReturnFlights.size() == 0) {
            return Optional.empty();
        }

        return Optional.of(FlightPair.builder()
                .outboundFlight(viableOutboundFlights.get(0).toDocument())
                .returnFlight(viableReturnFlights.get(0).toDocument())
                .build()
        );
    }

    @QueryHandler
    Boolean handle(CheckFlightAvailabilityQuery query) {
        return flightAvailabilityService.isAvailable(query);
    }

    @QueryHandler
    Optional<Double> handle(GetFlightBookingPriceQuery query) {
        Optional<Flight> flight = flightRepository.findById(query.flightNumber);
        if(flight.isEmpty()) {
            return Optional.empty();
        }
        Instant t1 = Instant.ofEpochSecond(flight.get().departureTimestamp);
        Instant t2 = Instant.ofEpochSecond(flight.get().arrivalTimestamp);
        double hrs = Duration.between(t1, t2).toSeconds() / 3600.0;

        return Optional.of(query.numSeats * hrs * PRICE_PER_SEAT_PER_HOUR);
    }

}
