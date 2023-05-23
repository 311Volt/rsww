package com.yetistudios.rsww.touroperator.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@Document
public class Offer {
    @Id
    private String id;
    private Hotel hotel;
    private Double price;
    private Integer numberOfOffers;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private List<Flight> flights;
}
