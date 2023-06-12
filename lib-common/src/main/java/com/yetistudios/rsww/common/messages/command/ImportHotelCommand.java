package com.yetistudios.rsww.common.messages.command;

import com.yetistudios.rsww.common.dto.HotelDocument;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class ImportHotelCommand {
    public HotelDocument hotelDocument;
}
