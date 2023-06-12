package com.yetistudios.rsww.common.messages.event;

import com.yetistudios.rsww.common.messages.entity.FlightBriefPair;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateOfferEvent {
    private String offerId;
    private Double price;
    private Integer numberOfOffers;
    private List<FlightBriefPair> flights;
}
