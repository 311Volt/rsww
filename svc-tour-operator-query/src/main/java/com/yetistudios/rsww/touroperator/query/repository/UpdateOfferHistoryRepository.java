package com.yetistudios.rsww.touroperator.query.repository;


import com.yetistudios.rsww.common.messages.entity.UpdateOfferHistory;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UpdateOfferHistoryRepository extends MongoRepository<UpdateOfferHistory,String> {
    List<UpdateOfferHistory> findTop10ByOrderByTimeOfUpdateDesc();
}
