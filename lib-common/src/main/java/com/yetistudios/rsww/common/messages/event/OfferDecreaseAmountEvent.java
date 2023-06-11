package com.yetistudios.rsww.common.messages.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OfferDecreaseAmountEvent {
    private String id;
    private String offerId;
    private Integer numberOfOffers;
}
