package com.yetistudios.rsww.rswwhotel.admin;

import com.yetistudios.rsww.common.messages.command.ImportFlightCommand;
import com.yetistudios.rsww.common.messages.command.ImportHotelCommand;
import com.yetistudios.rsww.rswwhotel.admin.service.AdminImportService;
import org.axonframework.commandhandling.CommandHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AdminImportCommandHandlers {

    @Autowired
    private AdminImportService adminImportService;

    @CommandHandler
    public void on(ImportHotelCommand hotelCommand) {
        adminImportService.importHotel(hotelCommand.hotelDocument);
    }

}
