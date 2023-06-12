package com.yetistudios.rsww.travelagency.command.event;

import com.yetistudios.rsww.common.messages.event.PayForReservationEvent;
import com.yetistudios.rsww.common.messages.event.PaymentValidEvent;
import com.yetistudios.rsww.common.messages.event.ReservationCanceledEvent;
import com.yetistudios.rsww.common.messages.event.ReservationCreatedEvent;
import com.yetistudios.rsww.common.messages.entity.Reservation;
import com.yetistudios.rsww.travelagency.common.repository.ReservationRepository;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.gateway.EventGateway;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReservationEventsHandler {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private EventGateway eventGateway;

    @EventHandler
    public void on(ReservationCreatedEvent event){
        Reservation reservation = new Reservation();

        BeanUtils.copyProperties(event, reservation);

        reservationRepository.insert(reservation);
    }

    @EventHandler
    public void on(ReservationCanceledEvent event){
        Reservation reservation = reservationRepository.findById(event.getReservationId()).get();

        reservationRepository.delete(reservation);
    }

    @EventHandler
    public void on(PayForReservationEvent event){
        Reservation reservation = reservationRepository.findById(event.getReservationId()).get();

        reservation.setPaid(true);

        reservationRepository.save(reservation);

        eventGateway.publish(new PaymentValidEvent(event.getReservationId()));
    }
}
