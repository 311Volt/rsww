package com.yetistudios.rsww.rswwgateway.controller;

import com.yetistudios.rsww.common.messages.command.DecreaseOfferAmountCommand;
import com.yetistudios.rsww.common.messages.command.CreateOfferCommand;
import com.yetistudios.rsww.common.dto.OfferDecreaseAmountDto;
import com.yetistudios.rsww.common.messages.command.UpdateOfferCommand;
import com.yetistudios.rsww.common.messages.entity.Offer;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/offer")
public class OfferCommandController {

    public OfferCommandController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    private CommandGateway commandGateway;

    @PostMapping
    public String addOffer(@RequestBody Offer offer) {

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

    @CrossOrigin
    @PostMapping("/{id}/decrease")
    public String decreaseOfferAmount(@RequestBody OfferDecreaseAmountDto offer, @RequestParam("id") String offerId) {
        DecreaseOfferAmountCommand decreaseOfferAmountCommand = DecreaseOfferAmountCommand.builder()
                .offerId(offerId)
                .numberOfOffers(offer.getNumberOfOffers())
                .build();
        return commandGateway.sendAndWait(decreaseOfferAmountCommand);
    }

    @PutMapping
    public String updateOffer(@RequestBody Offer offer) {

        UpdateOfferCommand createOfferCommand = UpdateOfferCommand.builder()
                .offerId(offer.getId())
                .flights(offer.getFlights())
                .price(offer.getSuggestedPrice())
                .numberOfOffers(offer.getNumberOfOffers())
                .build();

        return commandGateway.sendAndWait(createOfferCommand);
    }


}
