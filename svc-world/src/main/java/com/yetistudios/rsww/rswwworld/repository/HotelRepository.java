package com.yetistudios.rsww.rswwworld.repository;

import com.yetistudios.rsww.rswwworld.entity.Hotel;
import org.bson.types.ObjectId;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
@Profile("!test")
public interface HotelRepository extends MongoRepository<Hotel, ObjectId> {
}
