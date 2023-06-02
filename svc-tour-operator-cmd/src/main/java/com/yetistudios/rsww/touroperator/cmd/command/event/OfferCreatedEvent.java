package com.yetistudios.rsww.touroperator.cmd.command.event;

import com.yetistudios.rsww.touroperator.cmd.entity.Flight;
import com.yetistudios.rsww.touroperator.cmd.entity.Hotel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OfferCreatedEvent {
    private String offerId;
    private Hotel hotel;
    private Double price;
    private Integer numberOfOffers;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private List<Flight> flights;
}
