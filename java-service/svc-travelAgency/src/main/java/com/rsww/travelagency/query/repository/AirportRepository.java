package com.rsww.travelagency.query.repository;

import com.rsww.travelagency.query.entity.AirportEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirportRepository extends JpaRepository<AirportEntity, String> {
}
