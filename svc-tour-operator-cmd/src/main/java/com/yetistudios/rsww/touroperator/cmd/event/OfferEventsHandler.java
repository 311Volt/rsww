package com.yetistudios.rsww.touroperator.cmd.event;

import com.yetistudios.rsww.common.messages.event.OfferDecreaseAmountEvent;
import com.yetistudios.rsww.common.messages.event.*;
import com.yetistudios.rsww.touroperator.cmd.exception.OfferAmountIsInsufficientException;
import com.yetistudios.rsww.touroperator.cmd.exception.OfferDoesNotExistException;
import com.yetistudios.rsww.touroperator.cmd.repository.OfferRepository;
import com.yetistudios.rsww.common.messages.entity.Offer;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.gateway.EventGateway;
import org.axonframework.messaging.interceptors.ExceptionHandler;
import org.springframework.stereotype.Component;

@Component
@ProcessingGroup("offer")
public class OfferEventsHandler {

    public OfferEventsHandler(OfferRepository offerRepository, EventGateway eventGateway) {
        this.offerRepository = offerRepository;
        this.eventGateway = eventGateway;
    }

    private OfferRepository offerRepository;

    private EventGateway eventGateway;

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
            if(offer.getNumberOfOffers() - event.getNumberOfOffers() >= 0){
                offer.setNumberOfOffers(offer.getNumberOfOffers() - event.getNumberOfOffers());
                offerRepository.save(offer);
                if(!event.getReservationId().isEmpty()){
                    eventGateway.publish(new OfferDecreaseAmountSuccessfulEvent(event.getReservationId()));
                }
            } else {
                throw new OfferAmountIsInsufficientException("Insufficient amount of offer " + event.getOfferId() + " exists: " + offer.getNumberOfOffers() + " expected: " + event.getNumberOfOffers(), event.getReservationId());
            }

        },
            () -> {throw new OfferDoesNotExistException("No offer with id " + event.getOfferId());
        });
    }

    @EventHandler
    public void on(OfferIncreaseAmountEvent event){
        offerRepository.findById(event.getOfferId()).ifPresent(offer -> {
            offer.setNumberOfOffers(offer.getNumberOfOffers() + event.getNumberOfOffers());
            offerRepository.save(offer);
        });
    }

    @ExceptionHandler
    public void handle(OfferAmountIsInsufficientException exception) throws RuntimeException {
        if(!exception.reservationId.isEmpty()){
            eventGateway.publish(new OfferDecreaseAmountFailedEvent(exception.reservationId, exception.getMessage()));
        } else {
            throw exception;
        }
    }
}
