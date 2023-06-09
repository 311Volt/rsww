package com.yetistudios.rsww.common.messages.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayForReservationCommand {
    @TargetAggregateIdentifier
    private String reservationId;
    private String paid;
}
