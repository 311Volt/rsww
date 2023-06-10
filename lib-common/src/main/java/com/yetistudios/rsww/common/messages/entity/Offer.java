package com.yetistudios.rsww.messages.entity;

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
    public String id;
    public OfferHotel hotel;
    public Double price;
    public Integer numberOfOffers;
    public LocalDateTime startDate;
    public LocalDateTime endDate;
    public List<OfferFlight> flights;
}
