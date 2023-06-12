package com.yetistudios.rsww.common.messages.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminGenerateOffersCommand {
    public Integer numOffers;
}
