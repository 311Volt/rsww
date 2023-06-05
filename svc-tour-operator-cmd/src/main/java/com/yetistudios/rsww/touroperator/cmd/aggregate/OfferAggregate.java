package com.yetistudios.rsww.touroperator.cmd.aggregate;

import com.yetistudios.rsww.touroperator.cmd.commands.CreateOfferCommand;
import com.yetistudios.rsww.touroperator.cmd.commands.DecreaseOfferAmountCommand;
import com.yetistudios.rsww.touroperator.cmd.event.OfferCreatedEvent;
import com.yetistudios.rsww.touroperator.cmd.event.OfferDecreaseAmountEvent;
import com.yetistudios.rsww.touroperator.cmd.entity.Flight;
import com.yetistudios.rsww.touroperator.cmd.entity.Hotel;
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
    private String id;
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

    @CommandHandler
    public OfferAggregate(DecreaseOfferAmountCommand decreaseOfferAmountCommand) {

        OfferDecreaseAmountEvent offerDecreaseAmountEvent = new OfferDecreaseAmountEvent();

        BeanUtils.copyProperties(decreaseOfferAmountCommand,offerDecreaseAmountEvent);

        AggregateLifecycle.apply(offerDecreaseAmountEvent);
    }

    public OfferAggregate(){};

    @EventSourcingHandler
    public void on(OfferCreatedEvent offerCreatedEvent){
        this.id = offerCreatedEvent.getOfferId();
        this.offerId = offerCreatedEvent.getOfferId();
        this.hotel = offerCreatedEvent.getHotel();
        this.price = offerCreatedEvent.getPrice();
        this.numberOfOffers = offerCreatedEvent.getNumberOfOffers();
        this.startDate = offerCreatedEvent.getStartDate();
        this.endDate = offerCreatedEvent.getEndDate();
        this.flights = offerCreatedEvent.getFlights();
    }

    @EventSourcingHandler
    public void on(OfferDecreaseAmountEvent offerDecreaseAmountEvent){
        this.id = offerDecreaseAmountEvent.getId();
        this.offerId = offerDecreaseAmountEvent.getOfferId();
        this.numberOfOffers = offerDecreaseAmountEvent.getNumberOfOffers();
    }
}
