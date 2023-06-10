package com.yetistudios.rsww.messages.command;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
public class IncreaseOfferAmountCommand {

    @TargetAggregateIdentifier
    private String offerId;
    private String reservationId;
    private Integer numberOfOffers;
}
