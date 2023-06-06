package com.yetistudios.rsww.touroperator.query.queries;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetOffersQuery {
    private String destination;
    private String departure;
    private LocalDate startDate;
    private int people;
    private int page;
    private int pageSize;
}
