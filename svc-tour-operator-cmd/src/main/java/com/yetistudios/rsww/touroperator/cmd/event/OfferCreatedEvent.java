package com.yetistudios.rsww.touroperator.cmd.event;

import com.yetistudios.rsww.common.messages.entity.FlightBriefPair;
import com.yetistudios.rsww.common.messages.entity.HotelBrief;
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
    private HotelBrief hotelBrief;
    private Double price;
    private Integer numberOfOffers;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private List<FlightBriefPair> flights;
}
