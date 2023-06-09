package com.yetistudios.rsww.messages.query;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GetHotelBookingPriceQuery {
    public String hotelName;
    public List<String> birthDates;
}
