package com.yetistudios.rsww.travelagency.query.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class AirportEntity {
    @Id
    private String id;
    private String code;
    private String name;
    private Boolean forDeparture;
}
