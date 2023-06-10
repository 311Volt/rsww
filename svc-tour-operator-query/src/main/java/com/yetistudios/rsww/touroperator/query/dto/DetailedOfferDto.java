package com.yetistudios.rsww.touroperator.query.dto;

import com.yetistudios.rsww.messages.entity.OfferFlight;
import com.yetistudios.rsww.messages.entity.OfferHotel;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class DetailedOfferDto {
    private String id;
    private OfferHotel hotel;
    private Double price;
    private Integer numberOfOffers;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private List<OfferFlight> flights;
}
