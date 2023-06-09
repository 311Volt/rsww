package com.yetistudios.rsww.messages.query;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GetHotelBookingPriceQuery {
    public String hotelCode;
    public Long timestampBegin;
    public Long timestampEnd;
    public List<String> birthDates;
}
