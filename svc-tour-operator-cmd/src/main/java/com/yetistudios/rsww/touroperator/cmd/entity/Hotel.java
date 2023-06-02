package com.yetistudios.rsww.touroperator.cmd.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Hotel {
    private String id;
    public String name;
    public double standard;
    public String country;
}
