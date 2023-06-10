package com.yetistudios.rsww.common.messages.query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckHotelAvailabilityQuery {
    public String hotelCode;
    public long timestampBegin;
    public long timestampEnd;
}
