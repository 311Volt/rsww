package com.yetistudios.rsww.touroperator.cmd.repository;


import com.yetistudios.rsww.common.messages.entity.UpdateOfferHistory;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UpdateOfferHistoryRepository extends MongoRepository<UpdateOfferHistory,String> {
}
