package com.yetistudios.rsww.rswwworld.query.repository;

import com.yetistudios.rsww.rswwworld.query.entity.Airport;
import org.bson.types.ObjectId;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Profile("!test")
public interface AirportRepository extends MongoRepository<Airport, ObjectId> {
    public Optional<Airport> findByCode(String code);
}
