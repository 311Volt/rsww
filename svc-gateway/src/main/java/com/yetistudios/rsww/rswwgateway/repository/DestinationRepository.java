package com.yetistudios.rsww.rswwgateway.repository;

import com.yetistudios.rsww.rswwgateway.entity.DestinationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DestinationRepository extends JpaRepository<DestinationEntity, String> {
    List<DestinationEntity> findTop3ByOrderByPopularityDesc();
}
