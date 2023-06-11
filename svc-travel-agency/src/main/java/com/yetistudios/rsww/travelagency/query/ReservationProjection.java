package com.yetistudios.rsww.travelagency.query;

import com.yetistudios.rsww.common.dto.ReservationDto;
import com.yetistudios.rsww.common.messages.query.GetReservationQuery;
import com.yetistudios.rsww.travelagency.common.entity.Reservation;
import com.yetistudios.rsww.travelagency.common.repository.ReservationRepository;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

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
}
