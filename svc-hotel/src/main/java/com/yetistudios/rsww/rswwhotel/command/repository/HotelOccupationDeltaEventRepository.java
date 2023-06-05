package com.yetistudios.rsww.rswwhotel.command.repository;

import com.yetistudios.rsww.rswwhotel.command.event.HotelOccupationDeltaEvent;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelOccupationDeltaEventRepository extends MongoRepository<HotelOccupationDeltaEvent, ObjectId> {
    public List<HotelOccupationDeltaEvent> findByTimestampLessThan(Long timestamp);
    public List<HotelOccupationDeltaEvent> findByTimestampBetween(Long start, Long end);
}
