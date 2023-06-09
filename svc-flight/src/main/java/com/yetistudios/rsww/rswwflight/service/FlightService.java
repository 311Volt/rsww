package com.yetistudios.rsww.rswwflight.service;

import com.yetistudios.rsww.messages.misc.FlightDocument;
import com.yetistudios.rsww.rswwflight.entity.Flight;
import com.yetistudios.rsww.rswwflight.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FlightService {

    @Autowired
    public FlightRepository flightRepository;

    public void importFlight(FlightDocument document) {
        flightRepository.save(Flight.ofSourceDocument(document));
    }

}
