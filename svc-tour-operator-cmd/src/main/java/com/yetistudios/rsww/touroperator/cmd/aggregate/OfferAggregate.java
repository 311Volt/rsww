package com.yetistudios.rsww.touroperator.cmd.aggregate;

import com.yetistudios.rsww.common.messages.command.DecreaseOfferAmountCommand;
import com.yetistudios.rsww.common.messages.command.IncreaseOfferAmountCommand;
import com.yetistudios.rsww.common.messages.command.UpdateOfferCommand;
import com.yetistudios.rsww.common.messages.event.OfferDecreaseAmountEvent;
import com.yetistudios.rsww.common.messages.event.OfferIncreaseAmountEvent;
import com.yetistudios.rsww.common.messages.command.CreateOfferCommand;
import com.yetistudios.rsww.common.messages.entity.FlightBriefPair;
import com.yetistudios.rsww.common.messages.event.UpdateOfferEvent;
import com.yetistudios.rsww.touroperator.cmd.event.OfferCreatedEvent;
import com.yetistudios.rsww.common.messages.entity.HotelBrief;
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
    private HotelBrief hotelBrief;
    private Double price;
    private Integer numberOfOffers;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private List<FlightBriefPair> flights;
    private String reservationId;

    @CommandHandler
    public OfferAggregate(CreateOfferCommand createOfferCommand) {
        OfferCreatedEvent offerCreatedEvent = new OfferCreatedEvent();

        BeanUtils.copyProperties(createOfferCommand,offerCreatedEvent);

        AggregateLifecycle.apply(offerCreatedEvent);
    }

    @CommandHandler
    public void handle(DecreaseOfferAmountCommand decreaseOfferAmountCommand) {
        OfferDecreaseAmountEvent offerDecreaseAmountEvent = new OfferDecreaseAmountEvent();

        BeanUtils.copyProperties(decreaseOfferAmountCommand,offerDecreaseAmountEvent);

        AggregateLifecycle.apply(offerDecreaseAmountEvent);
    }

    public OfferAggregate(){};

    @EventSourcingHandler
    public void on(OfferCreatedEvent offerCreatedEvent){
        this.offerId = offerCreatedEvent.getOfferId();
        this.hotelBrief = offerCreatedEvent.getHotelBrief();
        this.price = offerCreatedEvent.getPrice();
        this.numberOfOffers = offerCreatedEvent.getNumberOfOffers();
        this.startDate = offerCreatedEvent.getStartDate();
        this.endDate = offerCreatedEvent.getEndDate();
        this.flights = offerCreatedEvent.getFlights();
    }

    @EventSourcingHandler
    public void on(OfferDecreaseAmountEvent offerDecreaseAmountEvent){
        this.numberOfOffers = offerDecreaseAmountEvent.getNumberOfOffers();
        this.reservationId = offerDecreaseAmountEvent.getReservationId();
    }

    @CommandHandler
    public void handle(IncreaseOfferAmountCommand increaseOfferAmountCommand) {

        OfferIncreaseAmountEvent offerIncreaseAmountEvent = new OfferIncreaseAmountEvent();

        BeanUtils.copyProperties(increaseOfferAmountCommand,offerIncreaseAmountEvent);

        AggregateLifecycle.apply(offerIncreaseAmountEvent);
    }

    @EventSourcingHandler
    public void on(OfferIncreaseAmountEvent offerIncreaseAmountEvent){
        this.numberOfOffers = offerIncreaseAmountEvent.getNumberOfOffers();
        this.reservationId = offerIncreaseAmountEvent.getReservationId();
    }

    @CommandHandler
    public void handle(UpdateOfferCommand command){
        UpdateOfferEvent event = new UpdateOfferEvent();

        BeanUtils.copyProperties(command,event);

        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(UpdateOfferEvent event){
        this.price = event.getPrice();
        this.numberOfOffers = event.getNumberOfOffers();
        this.flights = event.getFlights();
    }
}
