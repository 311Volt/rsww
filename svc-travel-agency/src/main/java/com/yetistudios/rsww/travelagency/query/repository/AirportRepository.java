package com.yetistudios.rsww.travelagency.query.repository;

import com.yetistudios.rsww.travelagency.query.entity.AirportEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirportRepository extends JpaRepository<AirportEntity, String> {
}
