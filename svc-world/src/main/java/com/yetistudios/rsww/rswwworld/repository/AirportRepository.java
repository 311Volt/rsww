package com.yetistudios.rsww.rswwworld.repository;

import com.yetistudios.rsww.rswwworld.entity.Airport;
import org.bson.types.ObjectId;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
@Profile("!test")
public interface AirportRepository extends MongoRepository<Airport, ObjectId> {

}
