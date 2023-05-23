package com.yetistudios.rsww.travelagency.query.service;

import com.yetistudios.rsww.travelagency.common.event.AirportCreatedEvent;
import com.yetistudios.rsww.travelagency.query.entity.AirportEntity;
import com.yetistudios.rsww.travelagency.query.repository.AirportRepository;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class QueryTravelAgencyService {
    private final AirportRepository repository;

    public QueryTravelAgencyService(AirportRepository repository) {
        this.repository = repository;
    }

    @EventHandler
    public void on(AirportCreatedEvent airportCreatedEvent){
        log.info("Handling AirportCreatedEvent...");
        AirportEntity airportEntity = new AirportEntity();

        airportEntity.setId(airportCreatedEvent.getId());
        airportEntity.setCode(airportCreatedEvent.getCode());
        airportEntity.setName(airportCreatedEvent.getName());
        airportEntity.setForDeparture(airportCreatedEvent.getForDeparture());

        repository.save(airportEntity);
    }
}
