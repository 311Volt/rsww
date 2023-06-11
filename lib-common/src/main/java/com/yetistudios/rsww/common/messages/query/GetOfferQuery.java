package com.yetistudios.rsww.common.messages.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetOfferQuery {
    private String offerId;
}
