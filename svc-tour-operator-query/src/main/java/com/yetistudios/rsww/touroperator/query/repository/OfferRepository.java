package com.yetistudios.rsww.touroperator.query.repository;

import com.yetistudios.rsww.touroperator.query.entity.Offer;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OfferRepository extends MongoRepository<Offer,String> {

}
