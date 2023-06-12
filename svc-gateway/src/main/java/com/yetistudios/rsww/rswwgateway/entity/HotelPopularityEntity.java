package com.yetistudios.rsww.rswwgateway.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "hotelsPopularities")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HotelPopularityEntity {
    @Id
    private String hotelId;
    private String hotelName;
    private int popularity;
}
