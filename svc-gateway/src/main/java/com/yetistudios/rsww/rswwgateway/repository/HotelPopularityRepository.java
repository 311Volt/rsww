package com.yetistudios.rsww.rswwgateway.repository;

import com.yetistudios.rsww.rswwgateway.entity.HotelPopularityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HotelPopularityRepository extends JpaRepository<HotelPopularityEntity, String> {
    List<HotelPopularityEntity> findTop3ByOrderByPopularityDesc();
}
