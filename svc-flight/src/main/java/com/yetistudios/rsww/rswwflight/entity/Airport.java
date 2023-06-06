package com.yetistudios.rsww.rswwflight.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Builder;

@Entity
@Builder
public class Airport {
    @Id
    public String code;
    public String name;
    public boolean forDeparture;
}
