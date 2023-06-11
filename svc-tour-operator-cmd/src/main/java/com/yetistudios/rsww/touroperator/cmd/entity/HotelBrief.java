package com.yetistudios.rsww.touroperator.cmd.entity;

import com.yetistudios.rsww.common.dto.HotelSummary;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HotelBrief {
    private String id;
    public String name;
    public double standard;
    public String country;

    public static HotelBrief ofSummary(HotelSummary hotelSummary) {
        return HotelBrief.builder()
                .id(hotelSummary.code)
                .name(hotelSummary.name)
                .standard(hotelSummary.standard)
                .country(hotelSummary.country)
                .build();
    }
}
