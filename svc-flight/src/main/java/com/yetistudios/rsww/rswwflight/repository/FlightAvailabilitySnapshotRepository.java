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
            WHERE f.id.flightNumber = :prm.flightNumber
            AND f.timestamp <= :prm.timestamp
            ORDER BY f.timestamp DESC
            LIMIT 1
    """)
    List<FlightAvailabilitySnapshot> getLastSnapshotBefore(@Param("prm") FlightNumAndTimestampId prm);

}
