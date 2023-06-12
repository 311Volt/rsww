package com.yetistudios.rsww.common.messages.command;

import com.yetistudios.rsww.common.messages.entity.FlightBriefPair;
import com.yetistudios.rsww.common.messages.entity.HotelBrief;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateOfferCommand {

    @TargetAggregateIdentifier
    private String offerId;
    private HotelBrief hotelBrief;
    private Double price;
    private Integer numberOfOffers;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private List<FlightBriefPair> flights;

}
