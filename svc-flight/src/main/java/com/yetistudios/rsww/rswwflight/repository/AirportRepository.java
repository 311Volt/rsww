package com.yetistudios.rsww.rswwflight.repository;

import com.yetistudios.rsww.rswwflight.entity.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirportRepository extends JpaRepository<Airport, String> {

}
