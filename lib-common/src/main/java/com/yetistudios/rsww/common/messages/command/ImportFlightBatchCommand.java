package com.yetistudios.rsww.common.messages.command;


import com.yetistudios.rsww.common.dto.FlightDocument;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImportFlightBatchCommand {
    public List<FlightDocument> flights;
}
