package com.yetistudios.rsww.travelagency.command.controller;

import com.yetistudios.rsww.travelagency.command.dto.CreateAirportRequest;
import com.yetistudios.rsww.travelagency.command.service.TravelAgencyCommandService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(value = "/travel-agency")
public class TravelAgencyController {

    private final TravelAgencyCommandService travelAgencyCommandService;

    public TravelAgencyController(TravelAgencyCommandService travelAgencyCommandService) {
        this.travelAgencyCommandService = travelAgencyCommandService;
    }

    @CrossOrigin
    @PostMapping(value = "/create-airport")
    public ResponseEntity<String> createUser(@RequestBody CreateAirportRequest request) {
        try {
            CompletableFuture<String> response = travelAgencyCommandService.createAirport(request);
            return new ResponseEntity<>(response.get(), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CrossOrigin
    @PostMapping(value = "/init-airport")
    public void initAirportData() {
        travelAgencyCommandService.initAirportData();
    }
}
