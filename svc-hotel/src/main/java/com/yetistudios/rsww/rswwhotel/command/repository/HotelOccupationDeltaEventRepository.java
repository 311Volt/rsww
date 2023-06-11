package com.yetistudios.rsww.rswwhotel.command.repository;

import com.yetistudios.rsww.rswwhotel.command.event.HotelOccupationDeltaEvent;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelOccupationDeltaEventRepository extends MongoRepository<HotelOccupationDeltaEvent, ObjectId> {
    List<HotelOccupationDeltaEvent> findByTimestampLessThan(Long timestamp);
    List<HotelOccupationDeltaEvent> findByTimestampBetween(Long start, Long end);
    List<HotelOccupationDeltaEvent> findByReservationId(String reservationId);
    void deleteAllByReservationId(String reservationId);
}
