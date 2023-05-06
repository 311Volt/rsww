package com.yetistudios.rsww.rswwworld.query.controller;

import com.yetistudios.rsww.rswwworld.query.entity.Airport;
import com.yetistudios.rsww.rswwworld.query.entity.Flight;
import com.yetistudios.rsww.rswwworld.query.entity.Hotel;
import com.yetistudios.rsww.rswwworld.query.service.AdminImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("admin")
@Profile("!test")
public class AdminImportController {

    @Autowired
    private AdminImportService adminImportService;

    @PostMapping("/import-airports")
    public void importAirports(@RequestBody List<Airport> airports) {
        adminImportService.importAirports(airports);
    }

    @PostMapping("/import-flights")
    public void importFlights(@RequestBody List<Flight> flights) {
        adminImportService.importFlights(flights);
    }

    @PostMapping("/import-hotels")
    public void importHotels(@RequestBody Map<String, Hotel> hotels) {
        adminImportService.importHotels(hotels);
    }
}
