package com.yetistudios.rsww.common.messages.command;

import com.yetistudios.rsww.common.dto.AirportDocument;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class ImportAirportCommand {
    public AirportDocument airportDocument;
}
