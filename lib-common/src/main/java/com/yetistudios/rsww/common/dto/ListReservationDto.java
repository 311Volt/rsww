package com.yetistudios.rsww.common.dto;

import com.yetistudios.rsww.common.messages.entity.Reservation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ListReservationDto {
    private List<Reservation> reservationList;
}
