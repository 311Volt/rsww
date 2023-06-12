package com.yetistudios.rsww.rswwgateway.controller;

import com.yetistudios.rsww.common.dto.AirportDocument;
import com.yetistudios.rsww.common.dto.FlightDocument;
import com.yetistudios.rsww.common.dto.HotelDocument;
import com.yetistudios.rsww.common.messages.command.ImportAirportCommand;
import com.yetistudios.rsww.common.messages.command.ImportFlightBatchCommand;
import com.yetistudios.rsww.common.messages.command.ImportFlightCommand;
import com.yetistudios.rsww.common.messages.command.ImportHotelCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/import")
public class AdminImportController {


    @Autowired
    private CommandGateway commandGateway;


    @PostMapping("/hotel")
    public void importAirport(@RequestBody HotelDocument hotelDocument) {
        commandGateway.sendAndWait(ImportHotelCommand.builder().hotelDocument(hotelDocument).build());
    }

    @PostMapping("/airport")
    public void importAirport(@RequestBody AirportDocument airport) {
        commandGateway.sendAndWait(ImportAirportCommand.builder().airportDocument(airport).build());
    }


    @PostMapping("/flight")
    public void importFlight(@RequestBody FlightDocument flightDocument) {
        commandGateway.sendAndWait(ImportFlightCommand.builder().flightDocument(flightDocument).build());
    }

    @PostMapping("/flight-batch")
    public void importFlightBatch(@RequestBody List<FlightDocument> flightDocuments) {
        commandGateway.sendAndWait(ImportFlightBatchCommand.builder().flights(flightDocuments).build());
    }
}
