package com.yetistudios.rsww.touroperator.cmd.repository;

import com.yetistudios.rsww.common.messages.entity.Offer;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OfferRepository extends MongoRepository<Offer,String> {

}
