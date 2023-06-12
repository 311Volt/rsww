package com.yetistudios.rsww.touroperator.cmd.generator;

import com.yetistudios.rsww.common.messages.command.AdminGenerateOffersCommand;
import org.axonframework.commandhandling.CommandHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OfferGeneratorCommandHandler {

    @Autowired
    private OfferGenerator offerGenerator;

    @CommandHandler
    public void handle(AdminGenerateOffersCommand command) {
        offerGenerator.generateOffers(command.numOffers);
    }

}
