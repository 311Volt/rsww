package com.yetistudios.rsww.travelagency.command.event;

import com.yetistudios.rsww.messages.event.ReservationCanceledEvent;
import com.yetistudios.rsww.messages.event.ReservationCreatedEvent;
import com.yetistudios.rsww.travelagency.common.entity.Reservation;
import com.yetistudios.rsww.travelagency.common.repository.ReservationRepository;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class ReservationEventsHandler {
    private ReservationRepository reservationRepository;

    public ReservationEventsHandler(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

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
}
