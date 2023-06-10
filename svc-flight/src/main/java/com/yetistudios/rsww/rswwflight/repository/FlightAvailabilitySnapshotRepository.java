package com.yetistudios.rsww.rswwflight.repository;

import com.yetistudios.rsww.rswwflight.entity.FlightAvailabilitySnapshot;
import com.yetistudios.rsww.rswwflight.entity.FlightNumAndTimestampId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;

public interface FlightAvailabilitySnapshotRepository
        extends JpaRepository<FlightAvailabilitySnapshot, FlightNumAndTimestampId> {

    @Query(nativeQuery = true, value = """
        SELECT * FROM flight_availability_snapshots snap
            WHERE snap.flight_number = :#{#prm.flightNumber}
            AND snap.timestamp <= :#{#prm.timestamp}
            ORDER BY snap.timestamp DESC
            LIMIT 1
    """)
    List<FlightAvailabilitySnapshot> getLastSnapshotBefore(@Param("prm") FlightNumAndTimestampId prm);

}
