package com.yetistudios.rsww.common.messages.command;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
public class PayForReservationCommand {
    @TargetAggregateIdentifier
    private String reservationId;
}
