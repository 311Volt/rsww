package com.yetistudios.rsww.rswwhotel.query.repository;

import com.yetistudios.rsww.rswwhotel.query.entity.Hotel;
import org.bson.types.ObjectId;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Profile("!test")
public interface HotelRepository extends MongoRepository<Hotel, ObjectId> {
    public Optional<Hotel> findByCode(String code);
    public boolean existsByCode(String code);
}
