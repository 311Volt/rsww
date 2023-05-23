package com.yetistudios.rsww.touroperator.command.event;

import com.yetistudios.rsww.touroperator.command.repository.OfferRepository;
import com.yetistudios.rsww.touroperator.entity.Offer;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.messaging.interceptors.ExceptionHandler;
import org.springframework.stereotype.Component;

@Component
@ProcessingGroup("offer")
public class OfferEventsHandler {

    public OfferEventsHandler(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }

    private OfferRepository offerRepository;

    @EventHandler
    public void on(OfferCreatedEvent event){
        Offer offer = Offer.builder()
                .id(event.getOfferId())
                .price(event.getPrice())
                .hotel(event.getHotel())
                .flights(event.getFlights())
                .numberOfOffers(event.getNumberOfOffers())
                .startDate(event.getStartDate())
                .endDate(event.getEndDate())
                .build();

        offerRepository.save(offer);
    }

    @ExceptionHandler
    public void handle(Exception exception) throws Exception {
        throw exception;
    }
}
