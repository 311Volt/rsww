package com.yetistudios.rsww.travelagency.common.repository;

import com.yetistudios.rsww.travelagency.common.entity.Reservation;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReservationRepository extends MongoRepository<Reservation,String> {

}
