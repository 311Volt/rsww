package com.yetistudios.rsww.touroperator.query.repository;

import com.yetistudios.rsww.common.messages.entity.Offer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import org.springframework.data.domain.Pageable;
import java.time.LocalDate;
import java.util.List;

public interface OfferRepository extends MongoRepository<Offer,String> {
    @Query("{$and: ["
            + "{'hotelBrief.country': {$regex: ?0, $options: 'i'}}, "
            + "{'flights': {"
            + "    $elemMatch: {"
            + "        'outboundFlight.departureAirportName': {$regex: ?1, $options: 'i'}"
            + "    }"
            + "}}, "
            + "{'startDate': {$gte: ?2}}, "
            + "{'numberOfOffers': {$gte: ?3}}"
            + "]}")
    List<Offer> findOffersByCriteria(String destination, String departure, LocalDate startDate, Integer numberOfOffers, Pageable pageable);
}
