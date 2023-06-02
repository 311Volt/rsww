package com.yetistudios.rsww.touroperator.cmd.command.controller;

import com.yetistudios.rsww.touroperator.cmd.command.commands.CreateOfferCommand;
import com.yetistudios.rsww.touroperator.cmd.entity.Offer;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/offer")
public class OfferCommandController {

    public OfferCommandController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    private CommandGateway commandGateway;

    @PostMapping
    public String addOffer(@RequestBody Offer offer){

        CreateOfferCommand createOfferCommand = CreateOfferCommand.builder()
                .offerId(UUID.randomUUID().toString())
                .hotel(offer.getHotel())
                .flights(offer.getFlights())
                .price(offer.getPrice())
                .numberOfOffers(offer.getNumberOfOffers())
                .startDate(offer.getStartDate())
                .endDate(offer.getEndDate())
                .build();
        String result = commandGateway.sendAndWait(createOfferCommand);

        return result;
    }
}
