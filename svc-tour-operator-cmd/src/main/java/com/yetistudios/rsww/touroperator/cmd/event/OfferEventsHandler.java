package com.yetistudios.rsww.touroperator.cmd.event;

import com.yetistudios.rsww.messages.event.OfferDecreaseAmountEvent;
import com.yetistudios.rsww.messages.event.OfferIncreaseAmountEvent;
import com.yetistudios.rsww.touroperator.cmd.exception.OfferDoesNotExistException;
import com.yetistudios.rsww.touroperator.cmd.repository.OfferRepository;
import com.yetistudios.rsww.touroperator.cmd.entity.Offer;
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
                .suggestedPrice(event.getPrice())
                .hotelBrief(event.getHotelBrief())
                .flights(event.getFlights())
                .numberOfOffers(event.getNumberOfOffers())
                .startDate(event.getStartDate())
                .endDate(event.getEndDate())
                .build();

        offerRepository.save(offer);
    }

    @EventHandler
    public void on(OfferDecreaseAmountEvent event){
        offerRepository.findById(event.getOfferId()).ifPresentOrElse(offer -> {
            offer.setNumberOfOffers(offer.getNumberOfOffers() - event.getNumberOfOffers());
            offerRepository.save(offer);
        }, () -> {throw new OfferDoesNotExistException("No offer with id " + event.getOfferId());});
    }

    @EventHandler
    public void on(OfferIncreaseAmountEvent event){
        offerRepository.findById(event.getOfferId()).ifPresent(offer -> {
            offer.setNumberOfOffers(offer.getNumberOfOffers() + event.getNumberOfOffers());
            offerRepository.save(offer);
        });
    }

    @ExceptionHandler
    public void handle(Exception exception) throws Exception {
        throw exception;
    }
}
