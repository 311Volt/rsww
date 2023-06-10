package com.yetistudios.rsww.rswwflight.repository;

import com.yetistudios.rsww.common.messages.query.FindBestFlightPairQuery;
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
            WHERE f.arrival_timestamp < :#{#fpq.latestAcceptableOutboundArrival}
            AND f.departure_airport_code = :#{#fpq.outboundDepartureAirportCode}
            AND f.arrival_airport_code = :#{#fpq.outboundArrivalAirportCode}
            ORDER BY f.arrival_timestamp DESC
            LIMIT 25
    """)
    List<Flight> getViableOutboundFlights(@Param("fpq") FindBestFlightPairQuery fpq);

    @Query(nativeQuery = true, value = """
        SELECT * FROM flights f
            WHERE f.departure_timestamp > :#{#fpq.earliestAcceptableReturnDeparture}
            AND f.departure_airport_code = :#{#fpq.outboundArrivalAirportCode}
            AND f.arrival_airport_code = :#{#fpq.outboundDepartureAirportCode}
            ORDER BY f.departure_timestamp ASC
            LIMIT 25
    """)
    List<Flight> getViableReturnFlights(@Param("fpq") FindBestFlightPairQuery fpq);
}
