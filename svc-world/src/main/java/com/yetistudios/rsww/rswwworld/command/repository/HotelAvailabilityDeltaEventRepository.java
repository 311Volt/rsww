package com.yetistudios.rsww.rswwworld.command.repository;

import com.yetistudios.rsww.rswwworld.command.event.HotelAvailabilityDeltaEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelAvailabilityDeltaEventRepository extends JpaRepository<HotelAvailabilityDeltaEvent, Long> {
    public List<HotelAvailabilityDeltaEvent> findByTimestampLessThan(Long timestamp);
    public List<HotelAvailabilityDeltaEvent> findByTimestampBetween(Long start, Long end);
}
