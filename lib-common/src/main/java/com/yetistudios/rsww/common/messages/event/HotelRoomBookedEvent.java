package com.yetistudios.rsww.common.messages.event;

import com.yetistudios.rsww.common.dto.HotelRoomVector;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HotelRoomBookedEvent {
    public String hotelCode;
    public HotelRoomVector amount;
}
