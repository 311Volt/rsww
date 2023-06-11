package com.yetistudios.rsww.rswwhotel.query.service;

import com.yetistudios.rsww.common.dto.HotelAvailabilityVector;
import com.yetistudios.rsww.common.messages.command.BookHotelCommand;
import com.yetistudios.rsww.common.messages.query.CheckHotelAvailabilityQuery;
import com.yetistudios.rsww.rswwhotel.command.event.HotelOccupationDeltaEvent;
import com.yetistudios.rsww.rswwhotel.command.repository.HotelOccupationDeltaEventRepository;
import com.yetistudios.rsww.rswwhotel.query.entity.Hotel;
import com.yetistudios.rsww.rswwhotel.query.entity.HotelOccupation;
import com.yetistudios.rsww.common.dto.HotelRoomVector;
import com.yetistudios.rsww.rswwhotel.query.repository.HotelRepository;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class HotelOccupationQueryService {

    @Autowired
    private HotelOccupationDeltaEventRepository hotelEventRepo;

    @Autowired
    private HotelRepository hotelRepository;

    public Optional<Pair<HotelOccupation, Long>> findLatestSnapshotBefore(String hotelCode, Long timestamp) {
        //TODO 2nd stage implement this
        return Optional.empty();
    }

    private HotelOccupation getInitialStateForReplay(String hotelCode, Long timestamp) {
        var latest = findLatestSnapshotBefore(hotelCode, timestamp);
        return latest.map(Pair::getFirst).orElseGet(() -> HotelOccupation.initial(hotelCode));
    }

    private List<HotelOccupationDeltaEvent> getEventsBetween(String hotelCode, Long beginTimestamp, Long endTimestamp) {
        var events = hotelEventRepo
                .findByTimestampBetween(beginTimestamp-1, endTimestamp+1).stream()
                .filter(ev -> ev.hotelCode.equals(hotelCode))
                .toList();
        return events.stream().sorted(Comparator.comparingLong(ev -> ev.timestamp)).toList();
    }

    public Optional<HotelOccupation> getOccupationAsOf(String hotelCode, Long timestamp) {
        if(!hotelRepository.existsByCode(hotelCode)) {
            return Optional.empty();
        }

        var latest = findLatestSnapshotBefore(hotelCode, timestamp);
        long timestampBegin = latest.isEmpty() ? Long.MIN_VALUE : latest.get().getSecond();
        long timestampEnd = timestamp-1;

        HotelOccupation initial = latest.map(Pair::getFirst).orElseGet(() -> HotelOccupation.initial(hotelCode));
        var remainingEvents = getEventsBetween(hotelCode, timestampBegin, timestampEnd);

        initial.consumeAll(remainingEvents);
        return Optional.of(initial);
    }

    @SneakyThrows
    public Optional<HotelRoomVector> getMaxOccupationDuring(String hotelCode, Long beginTimestamp, Long endTimestamp) {

        if(!hotelRepository.existsByCode(hotelCode)) {
            return Optional.empty();
        }

        var latest = findLatestSnapshotBefore(hotelCode, beginTimestamp);
        var remainingEvents = getEventsBetween(hotelCode, beginTimestamp, endTimestamp);

        HotelOccupation occupation = getInitialStateForReplay(hotelCode, beginTimestamp);
        HotelRoomVector result = occupation.toVector();
        log.debug("{} events found", remainingEvents.size());
        for(HotelOccupationDeltaEvent event: remainingEvents) {
            log.debug("{} consuming event: {} / {},{},{}", hotelCode, event.timestamp, event.deltaSingleRooms, event.deltaDoubleRooms, event.deltaTripleRooms);
            occupation.consumeEvent(event);
            result.max(occupation.toVector());
        }
        return Optional.of(result);
    }


    public HotelAvailabilityVector checkHotelAvailability(CheckHotelAvailabilityQuery query) {
        if(!hotelRepository.existsByCode(query.hotelCode)) {
            return new HotelAvailabilityVector(0, 0, 0);
        }

        Hotel hotel = hotelRepository.findByCode(query.hotelCode).orElseThrow();
        HotelRoomVector vec = getMaxOccupationDuring(
                query.hotelCode, query.timestampBegin, query.timestampEnd).orElseThrow();

        return new HotelAvailabilityVector(
                hotel.numSingleRooms - vec.numSingleRooms,
                hotel.numDoubleRooms - vec.numDoubleRooms,
                hotel.numTripleRooms - vec.numTripleRooms
        );
    }

    public Boolean checkReservationAvailability(BookHotelCommand reservation) {
        int availability = checkHotelAvailability(
                CheckHotelAvailabilityQuery
                        .builder()
                        .hotelCode(reservation.hotelCode)
                        .timestampBegin(reservation.timestampBegin)
                        .timestampEnd(reservation.timestampEnd)
                        .build())
                .compareTo(reservation.getVec());

        return availability >= 0;
    }


}
