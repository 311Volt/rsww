package com.yetistudios.rsww.rswwflight.restadmin;

import com.yetistudios.rsww.common.dto.FlightDocument;
import com.yetistudios.rsww.rswwflight.entity.Airport;
import com.yetistudios.rsww.rswwflight.repository.AirportRepository;
import com.yetistudios.rsww.rswwflight.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("admin")
public class FlightAdminController {

    @Autowired
    private FlightService flightService;

    @Autowired
    private AirportRepository airportRepository;

    @PostMapping("/import-airport")
    public void importAirport(@RequestBody Airport airport) {
        airportRepository.save(airport);
    }

    @PostMapping("/import-flight")
    public void importFlight(@RequestBody FlightDocument flightDocument) {
        flightService.importFlight(flightDocument);
    }

    @PostMapping("/import-flight-batch")
    public void importFlightBatch(@RequestBody List<FlightDocument> flightDocuments) {
        flightDocuments.forEach(flightService::importFlight);
    }
}
