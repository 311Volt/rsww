package com.yetistudios.rsww.rswwworld.query.service;

import com.yetistudios.rsww.rswwworld.command.repository.HotelAvailabilityDeltaEventRepository;
import com.yetistudios.rsww.rswwworld.query.entity.HotelAvailability;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HotelAvailabilityQueryService {

    @Autowired
    private HotelAvailabilityDeltaEventRepository hotelEventRepo;

    public Optional<Pair<HotelAvailability, Long>> findLatestSnapshotBefore(String hotelCode, Long timestamp) {
        throw new RuntimeException("stub");
    }

    public Optional<HotelAvailability> getAvailabilityAsOf(String hotelCode, Long timestamp) {
        var latest = findLatestSnapshotBefore(hotelCode, timestamp);
        if(latest.isEmpty()) {
            return Optional.empty();
        }
        HotelAvailability snapshot = latest.get().getFirst();
        Long snapshotTimestamp = latest.get().getSecond();
        var remainingEvents = hotelEventRepo.findByTimestampBetween(snapshotTimestamp, timestamp-1);
        snapshot.consumeAll(remainingEvents);
        return Optional.of(snapshot);
    }

}
