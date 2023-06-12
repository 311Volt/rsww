package com.yetistudios.rsww.rswwflight.entity;

import com.yetistudios.rsww.common.dto.AirportDocument;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "airports")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Airport {
    @Id
    public String code;
    public String name;
    public boolean forDeparture;

    public static Airport ofDocument(AirportDocument document) {
        return Airport.builder()
                .code(document.code)
                .name(document.name)
                .forDeparture(document.forDeparture)
                .build();
    }
}
