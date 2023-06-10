package com.yetistudios.rsww.touroperator.cmd.controller;

import com.yetistudios.rsww.touroperator.cmd.commands.CreateOfferCommand;
import com.yetistudios.rsww.touroperator.cmd.commands.DecreaseOfferAmountCommand;
import com.yetistudios.rsww.touroperator.cmd.dto.OfferDecreaseAmountDto;
import com.yetistudios.rsww.touroperator.cmd.entity.Offer;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

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
                .hotelBrief(offer.getHotelBrief())
                .flights(offer.getFlights())
                .price(offer.getSuggestedPrice())
                .numberOfOffers(offer.getNumberOfOffers())
                .startDate(offer.getStartDate())
                .endDate(offer.getEndDate())
                .build();

        return commandGateway.sendAndWait(createOfferCommand);
    }

    @PostMapping("/decrease")
    public String decreaseOfferAmount(@RequestBody OfferDecreaseAmountDto offer, @RequestParam("id") String offerId){
        DecreaseOfferAmountCommand decreaseOfferAmountCommand = DecreaseOfferAmountCommand.builder()
                .id(UUID.randomUUID().toString())
                .offerId(offerId)
                .numberOfOffers(offer.getNumberOfOffers())
                .build();
        return commandGateway.sendAndWait(decreaseOfferAmountCommand);
    }
}
