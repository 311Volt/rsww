package com.yetistudios.rsww.touroperator.query.dto;

import com.yetistudios.rsww.common.messages.entity.Offer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OfferListDto {
    List<Offer> offerList;
}
