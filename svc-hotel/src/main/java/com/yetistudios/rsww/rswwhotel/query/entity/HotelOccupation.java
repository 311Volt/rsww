package com.yetistudios.rsww.rswwhotel.query.entity;

import com.yetistudios.rsww.rswwhotel.command.event.HotelOccupationDeltaEvent;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class HotelOccupation implements Cloneable {

    public String hotelCode;
    private int takenSingleRooms = 0;
    private int takenDoubleRooms = 0;
    private int takenTripleRooms = 0;

    public static HotelOccupation initial(String hotelCode) {
        return HotelOccupation.builder().hotelCode(hotelCode).build();
    }

    public void consumeEvent(HotelOccupationDeltaEvent event) {
        takenSingleRooms += event.deltaSingleRooms;
        takenDoubleRooms += event.deltaDoubleRooms;
        takenTripleRooms += event.deltaTripleRooms;
    }

    public void consumeAll(Iterable<HotelOccupationDeltaEvent> events) {
        events.forEach(this::consumeEvent);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
