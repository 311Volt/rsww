package com.yetistudios.rsww.rswwhotel.query.repository;

import com.yetistudios.rsww.rswwhotel.query.entity.Hotel;
import org.bson.types.ObjectId;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Profile("!test")
public interface HotelRepository extends MongoRepository<Hotel, ObjectId> {
    public Optional<Hotel> findByCode(String code);
    public boolean existsByCode(String code);
    public void deleteAllByCode(String code);

    @Aggregation(pipeline = {"{$match: {country: ?0}}", "{$sample: {size: 1}}"})
    public AggregationResults<Hotel> findRandomByCountry(String country);

    @Aggregation(pipeline = {"{$sample: {size: 1}}"})
    public AggregationResults<Hotel> findRandom();
}
