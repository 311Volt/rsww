package com.yetistudios.rsww.touroperator.cmd.commands;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
public class DecreaseOfferAmountCommand {

    @TargetAggregateIdentifier
    private String id;
    private String offerId;
    private Integer numberOfOffers;
}
