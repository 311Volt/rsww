package com.yetistudios.rsww.rswwflight.scheduled;

import com.yetistudios.rsww.rswwflight.entity.Flight;
import com.yetistudios.rsww.rswwflight.repository.FlightAvailabilityRepository;
import com.yetistudios.rsww.rswwflight.repository.FlightRepository;
import com.yetistudios.rsww.rswwflight.service.FlightAvailabilityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class NewFlightScanner {

    @Autowired
    private FlightAvailabilityService flightAvailabilityService;

    @Autowired
    private FlightAvailabilityRepository flightAvailabilityRepository;

    @Autowired
    private FlightRepository flightRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void onStartup() {
        scan();
    }

    @Scheduled(fixedDelay = 5, timeUnit = TimeUnit.MINUTES)
    public void scan() {
        log.debug("scanning for new flights...");
        int numRegistered = 0;
        for(Flight flight: flightRepository.findAll()) {
            numRegistered++;
            if(!flightAvailabilityRepository.existsById(flight.flightNumber)) {
                flightAvailabilityService.registerFlight(flight.flightNumber);
            }
        }
        if(numRegistered > 0) {
            log.info("{} new flights registered", numRegistered);
        }

    }

}
