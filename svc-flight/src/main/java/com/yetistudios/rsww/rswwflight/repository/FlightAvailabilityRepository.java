package com.yetistudios.rsww.rswwflight.repository;

import com.yetistudios.rsww.rswwflight.entity.FlightAvailability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightAvailabilityRepository extends JpaRepository<FlightAvailability, Integer> {

}
