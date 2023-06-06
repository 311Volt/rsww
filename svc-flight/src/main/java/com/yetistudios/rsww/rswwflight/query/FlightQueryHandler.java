package com.yetistudios.rsww.rswwflight.query;

import com.yetistudios.rsww.messages.misc.FlightPair;
import com.yetistudios.rsww.messages.query.FindBestFlightPairQuery;
import com.yetistudios.rsww.rswwflight.entity.Flight;
import com.yetistudios.rsww.rswwflight.repository.AirportRepository;
import com.yetistudios.rsww.rswwflight.repository.FlightRepository;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class FlightQueryHandler {

    @Autowired
    private AirportRepository airportRepository;

    @Autowired
    private FlightRepository flightRepository;


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

}
