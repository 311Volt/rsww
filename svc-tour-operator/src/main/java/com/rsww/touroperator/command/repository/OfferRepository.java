package com.rsww.touroperator.command.repository;

import com.rsww.touroperator.entity.Offer;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OfferRepository extends MongoRepository<Offer,String> {

}
