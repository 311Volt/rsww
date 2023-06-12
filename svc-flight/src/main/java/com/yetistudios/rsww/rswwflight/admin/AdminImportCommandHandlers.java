package com.yetistudios.rsww.rswwflight.admin;

import com.yetistudios.rsww.common.messages.command.ImportAirportCommand;
import com.yetistudios.rsww.common.messages.command.ImportFlightBatchCommand;
import com.yetistudios.rsww.common.messages.command.ImportFlightCommand;
import org.axonframework.commandhandling.CommandHandler;
import org.springframework.beans.factory.annotation.Autowired;

public class AdminImportCommandHandlers {

    @Autowired
    private AdminImportService adminImportService;

    @CommandHandler
    public void on(ImportAirportCommand command) {
        adminImportService.importAirport(command.airportDocument);
    }

    @CommandHandler
    public void on(ImportFlightCommand command) {
        adminImportService.importFlight(command.flightDocument);
    }

    @CommandHandler
    public void on(ImportFlightBatchCommand command) {
        adminImportService.importFlightBatch(command.flights);
    }

}
