package com.rsww.touroperator.command.aggregate;

import com.rsww.touroperator.command.commands.CreateOfferCommand;
import com.rsww.touroperator.command.event.OfferCreatedEvent;
import com.rsww.touroperator.entity.Flight;
import com.rsww.touroperator.entity.Hotel;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
import java.util.List;

@Aggregate
public class OfferAggregate {

    @AggregateIdentifier
    private String offerId;
    private Hotel hotel;
    private Double price;
    private Integer numberOfOffers;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private List<Flight> flights;

    @CommandHandler
    public OfferAggregate(CreateOfferCommand createOfferCommand) {
        OfferCreatedEvent offerCreatedEvent = new OfferCreatedEvent();

        BeanUtils.copyProperties(createOfferCommand,offerCreatedEvent);

        AggregateLifecycle.apply(offerCreatedEvent);
    }

    @EventSourcingHandler
    public void on(OfferCreatedEvent offerCreatedEvent){
        this.offerId = offerCreatedEvent.getOfferId();
        this.hotel = offerCreatedEvent.getHotel();
        this.price = offerCreatedEvent.getPrice();
        this.numberOfOffers = offerCreatedEvent.getNumberOfOffers();
        this.startDate = offerCreatedEvent.getStartDate();
        this.endDate = offerCreatedEvent.getEndDate();
        this.flights = offerCreatedEvent.getFlights();
    }
}
