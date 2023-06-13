package com.yetistudios.rsww.touroperator.cmd.generator;

import com.yetistudios.rsww.common.messages.command.AdminGenerateOffersCommand;
import com.yetistudios.rsww.common.messages.command.CreateOfferCommand;
import com.yetistudios.rsww.common.messages.entity.Offer;
import com.yetistudios.rsww.touroperator.cmd.repository.OfferRepository;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class OfferGeneratorCommandHandler {

    @Autowired
    private OfferGenerator offerGenerator;

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private CommandGateway commandGateway;

    @CommandHandler
    public void handle(AdminGenerateOffersCommand command) {
        offerGenerator.generateOffers(command.numOffers);
    }

}
