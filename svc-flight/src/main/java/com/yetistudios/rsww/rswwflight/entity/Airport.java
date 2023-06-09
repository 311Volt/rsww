package com.yetistudios.rsww.rswwflight.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;

@Entity
@Table(name = "airports")
@Builder
public class Airport {
    @Id
    public String code;
    public String name;
    public boolean forDeparture;
}
