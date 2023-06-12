package com.yetistudios.rsww.rswwgateway.repository;

import com.yetistudios.rsww.rswwgateway.entity.AirportsPopularityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AirportPopularityRepository extends JpaRepository<AirportsPopularityEntity, String> {
    List<AirportsPopularityEntity> findTop3ByOrderByPopularityDesc();
}
