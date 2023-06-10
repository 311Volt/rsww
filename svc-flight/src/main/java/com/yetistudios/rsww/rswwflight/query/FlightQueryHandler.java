package com.yetistudios.rsww.rswwflight.query;

import com.yetistudios.rsww.common.dto.FlightDocument;
import com.yetistudios.rsww.common.dto.FlightPair;
import com.yetistudios.rsww.common.dto.FlightPairList;
import com.yetistudios.rsww.common.messages.query.*;
import com.yetistudios.rsww.rswwflight.entity.Airport;
import com.yetistudios.rsww.rswwflight.entity.Flight;
import com.yetistudios.rsww.rswwflight.repository.AirportRepository;
import com.yetistudios.rsww.rswwflight.repository.FlightRepository;
import com.yetistudios.rsww.rswwflight.service.FlightAvailabilityService;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
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
    private static final Duration MAX_ACCEPTABLE_OUT_ADVANCE = Duration.of(8, ChronoUnit.HOURS);
    private static final Duration MAX_ACCEPTABLE_RET_DELAY = Duration.of(8, ChronoUnit.HOURS);


    @QueryHandler
    Optional<FlightPair> handle(FindBestFlightPairQuery query) {
        List<Flight> viableOutboundFlights = flightRepository.getViableOutboundFlights(query);
        List<Flight> viableReturnFlights = flightRepository.getViableReturnFlights(query);

        if(viableOutboundFlights.size() == 0 || viableReturnFlights.size() == 0) {
            return Optional.empty();
        }

        var outboundFlight = viableOutboundFlights.get(0).toDocument();
        var returnFlight = viableReturnFlights.get(0).toDocument();

        if(outboundFlight.arrival.getEpochSeconds() - query.latestAcceptableOutboundArrival > MAX_ACCEPTABLE_OUT_ADVANCE.toSeconds()) {
            return Optional.empty();
        }
        if(query.earliestAcceptableReturnDeparture - returnFlight.departure.getEpochSeconds() > MAX_ACCEPTABLE_RET_DELAY.toSeconds()) {
            return Optional.empty();
        }

        return Optional.of(FlightPair.builder()
                .outboundFlight(viableOutboundFlights.get(0).toDocument())
                .returnFlight(viableReturnFlights.get(0).toDocument())
                .build()
        );
    }

    @QueryHandler
    FlightPairList handle(FindAllViableFlightPairsQuery query) {
        List<Airport> viableOutboundDepartureAirports = airportRepository.findByForDeparture(true);

        List<FlightPair> result = new ArrayList<>();
        for(Airport airport: viableOutboundDepartureAirports) {
            var singularQuery = FindBestFlightPairQuery.builder()
                    .latestAcceptableOutboundArrival(query.latestAcceptableOutboundArrival)
                    .earliestAcceptableReturnDeparture(query.earliestAcceptableReturnDeparture)
                    .outboundArrivalAirportCode(query.outboundArrivalAirportCode)
                    .outboundDepartureAirportCode(airport.code)
                    .build();
            handle(singularQuery).map(result::add);
        }

        return FlightPairList.builder().flights(result).build();
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

    @QueryHandler
    Optional<FlightDocument> handle(GetFlightInfoQuery query) {
        return flightRepository.findByFlightNumber(query.flightNumber).stream().findFirst().map(Flight::toDocument);
    }

}
