package com.yetistudios.rsww.travelagency.query;

import com.yetistudios.rsww.common.dto.ListReservationDto;
import com.yetistudios.rsww.common.dto.ReservationDto;
import com.yetistudios.rsww.common.messages.query.GetReservationQuery;
import com.yetistudios.rsww.common.messages.entity.Reservation;
import com.yetistudios.rsww.common.messages.query.GetReservationsQuery;
import com.yetistudios.rsww.travelagency.common.repository.ReservationRepository;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReservationProjection {
    private ReservationRepository reservationRepository;

    public ReservationProjection(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @QueryHandler
    public ReservationDto handle(GetReservationQuery query){
        Reservation reservation = reservationRepository.findById(query.getReservationId()).orElseThrow(() -> new RuntimeException("No such reserwation."));
        ReservationDto reservationDto = new ReservationDto();

        BeanUtils.copyProperties(reservation,reservationDto);
        return reservationDto;
    }

    @QueryHandler
    public ListReservationDto handle(GetReservationsQuery query){
        List<Reservation> reservations = reservationRepository.findAllByClientId(query.getUserId());
        ListReservationDto listReservationDto = new ListReservationDto(reservations);

        return listReservationDto;
    }
}
