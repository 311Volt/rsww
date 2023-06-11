package com.yetistudios.rsww.common.messages.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CancelReservationCommand {
    @TargetAggregateIdentifier
    private String reservationId;
}
