package com.yetistudios.rsww.travelagency.common.repository;

import com.yetistudios.rsww.common.messages.entity.Reservation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ReservationRepository extends MongoRepository<Reservation,String> {
    List<Reservation> findAllByClientId(String clientId);
}
