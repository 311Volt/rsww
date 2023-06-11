package com.yetistudios.rsww.common.messages.query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetHotelBookingPriceQuery {
    public String hotelCode;
    public Long timestampBegin;
    public Long timestampEnd;
    public List<String> birthDates;
}
