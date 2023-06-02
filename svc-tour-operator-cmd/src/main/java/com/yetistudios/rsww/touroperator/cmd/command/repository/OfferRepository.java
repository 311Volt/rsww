package com.yetistudios.rsww.touroperator.cmd.command.repository;

import com.yetistudios.rsww.touroperator.cmd.entity.Offer;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OfferRepository extends MongoRepository<Offer,String> {

}
