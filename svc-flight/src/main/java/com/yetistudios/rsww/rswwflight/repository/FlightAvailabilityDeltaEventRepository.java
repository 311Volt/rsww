package com.yetistudios.rsww.rswwflight.repository;

import com.yetistudios.rsww.rswwflight.entity.FlightAvailabilityDeltaEventEntity;
import com.yetistudios.rsww.rswwflight.entity.FlightNumAndTimestampId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FlightAvailabilityDeltaEventRepository
        extends JpaRepository<FlightAvailabilityDeltaEventEntity, FlightNumAndTimestampId> {

    @Query(nativeQuery = true, value = """
        SELECT * FROM flight_booking_events ev
            WHERE ev.flight_number = :#{#prm.flightNumber}
            AND ev.timestamp > :#{#prm.timestamp}
            ORDER BY ev.timestamp ASC
    """)
    List<FlightAvailabilityDeltaEventEntity> findAllEventsAfter(@Param("prm") FlightNumAndTimestampId prm);

    List<FlightAvailabilityDeltaEventEntity> findByReservationId(String reservationId);

}
