package com.yetistudios.rsww.travelagency.common.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Reservation {
    @Id
    private String reservationId;
    private String offerId;
    private String clientId;
    private String departureAirportName;
    private double price;
    private int nrOfPeople;
}
