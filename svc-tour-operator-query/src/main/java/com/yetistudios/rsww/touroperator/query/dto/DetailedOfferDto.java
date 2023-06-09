package com.yetistudios.rsww.touroperator.query.dto;

import com.yetistudios.rsww.touroperator.query.entity.Flight;
import com.yetistudios.rsww.touroperator.query.entity.Hotel;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class DetailedOfferDto {
    private String id;
    private Hotel hotel;
    private Double price;
    private Integer numberOfOffers;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private List<Flight> flights;
}
