package com.yetistudios.rsww.rswwflight.repository;

import com.yetistudios.rsww.messages.query.FindBestFlightPairQuery;
import com.yetistudios.rsww.rswwflight.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Integer> {

    @Query(nativeQuery = true, value = """
        SELECT * FROM flights f 
            WHERE f.arrivalTimestamp <= :query.latestAcceptableOutboundArrival
            AND f.departureAirportCode = :query.outboundDepartureAirportCode
            AND f.arrivalAirportCode = :query.outboundArrivalAirportCode
            ORDER BY f.arrivalTimestamp DESC
            LIMIT 25
    """)
    List<Flight> getViableOutboundFlights(@Param("query") FindBestFlightPairQuery query);

    @Query(nativeQuery = true, value = """
        SELECT * FROM flights f 
            WHERE f.departureTimestamp > :query.earliestAcceptableReturnDeparture
            AND f.departureAirportCode = :query.outboundArrivalAirportCode
            AND f.arrivalAirportCode = :query.outboundDepartureAirportCode
            ORDER BY f.departureTimestamp ASC
            LIMIT 25
    """)
    List<Flight> getViableReturnFlights(@Param("query") FindBestFlightPairQuery query);
}
