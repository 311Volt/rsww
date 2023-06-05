package com.yetistudios.rsww.touroperator.cmd.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OfferDecreaseAmountDto {
    private Integer numberOfOffers;
}
