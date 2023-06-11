package com.yetistudios.rsww.common.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HotelSummary {
    public String code;
    public String name;
    public double standard;
    public double latitude;
    public double longitude;
    public String airportCode;
    public String country;
}
