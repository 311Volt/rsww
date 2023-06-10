package com.yetistudios.rsww.messages.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OfferHotel {
    public String code;
    public String name;
    public double standard;
    public String country;
}
