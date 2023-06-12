package com.yetistudios.rsww.common.messages.command;

import com.yetistudios.rsww.common.messages.entity.FlightBriefPair;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateOfferCommand {

    @TargetAggregateIdentifier
    private String offerId;
    private Double price;
    private Integer numberOfOffers;
    private List<FlightBriefPair> flights;
}