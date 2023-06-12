package com.yetistudios.rsww.travelagency.command.aggregate;

import com.yetistudios.rsww.common.messages.command.CancelReservationCommand;
import com.yetistudios.rsww.common.messages.command.PayForReservationCommand;
import com.yetistudios.rsww.common.messages.event.PayForReservationEvent;
import com.yetistudios.rsww.common.messages.event.ReservationCanceledEvent;
import com.yetistudios.rsww.common.messages.event.ReservationCreatedEvent;
import com.yetistudios.rsww.common.messages.command.CreateReservationCommand;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

@Aggregate
@NoArgsConstructor
public class ReservationAgregate {

    @AggregateIdentifier
    private String reservationId;
    private String offerId;
    private String clientId;
    private double price;
    private String departureAirportName;
    private int nrOfPeople;
    public int numSingleRooms;
    public int numDoubleRooms;
    public int numTripleRooms;
    private boolean paid;

    @CommandHandler
    public ReservationAgregate(CreateReservationCommand command){
        ReservationCreatedEvent reservationCreatedEvent = new ReservationCreatedEvent();

        BeanUtils.copyProperties(command, reservationCreatedEvent);

        AggregateLifecycle.apply(reservationCreatedEvent);
    }

    @EventSourcingHandler
    public void on(ReservationCreatedEvent event){
        this.reservationId = event.getReservationId();
        this.offerId = event.getOfferId();
        this.clientId = event.getClientId();
        this.price = event.getPrice();
        this.departureAirportName = event.getDepartureAirportName();
        this.nrOfPeople = event.getNrOfPeople();
        this.numDoubleRooms = event.getNumDoubleRooms();
        this.numSingleRooms = event.getNumSingleRooms();
        this.numTripleRooms = event.getNumTripleRooms();
        this.paid = event.isPaid();
    }

    @CommandHandler
    public void handle(CancelReservationCommand command){
        ReservationCanceledEvent reservationCanceledEvent = new ReservationCanceledEvent();

        BeanUtils.copyProperties(command, reservationCanceledEvent);

        AggregateLifecycle.apply(reservationCanceledEvent);
    }

    @EventSourcingHandler
    public void on(ReservationCanceledEvent event){
    }

    @CommandHandler
    public void handle(PayForReservationCommand command){
        PayForReservationEvent event = new PayForReservationEvent();

        BeanUtils.copyProperties(command, event);

        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(PayForReservationEvent event){
        this.paid = true;
    }
}
