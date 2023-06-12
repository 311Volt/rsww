package com.yetistudios.rsww.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class AirportDocument {
    public String code;
    public String name;
    public boolean forDeparture;
}
