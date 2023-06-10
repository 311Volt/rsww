package com.yetistudios.rsww.touroperator.cmd.entity;

import com.yetistudios.rsww.common.dto.HotelSummary;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Hotel {
    private String id;
    public String name;
    public double standard;
    public String country;

    public static Hotel ofSummary(HotelSummary hotelSummary) {
        return Hotel.builder()
                .id(hotelSummary.code)
                .name(hotelSummary.name)
                .standard(hotelSummary.standard)
                .country(hotelSummary.country)
                .build();
    }
}
