package com.yetistudios.rsww.rswwflight.admin;

import com.yetistudios.rsww.common.dto.AirportDocument;
import com.yetistudios.rsww.common.dto.FlightDocument;
import com.yetistudios.rsww.rswwflight.entity.Airport;
import com.yetistudios.rsww.rswwflight.entity.Flight;
import com.yetistudios.rsww.rswwflight.repository.AirportRepository;
import com.yetistudios.rsww.rswwflight.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminImportService {

    @Autowired
    private AirportRepository airportRepository;

    @Autowired
    private FlightRepository flightRepository;

    public void importAirport(AirportDocument document) {
        airportRepository.save(Airport.ofDocument(document));
    }

    public void importFlight(FlightDocument flightDocument) {
        flightRepository.save(Flight.ofSourceDocument(flightDocument));
    }

    public void importFlightBatch(List<FlightDocument> flightDocuments) {
        flightDocuments.forEach(this::importFlight);
    }

}
