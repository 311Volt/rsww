package com.yetistudios.rsww.rswwhotel.query;

import com.yetistudios.rsww.common.messages.query.CheckHotelAvailabilityQuery;
import com.yetistudios.rsww.rswwhotel.query.service.HotelOccupationQueryService;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HotelOccupationQueryHandler {

    @Autowired
    HotelOccupationQueryService service;

    @QueryHandler
    Boolean handle(CheckHotelAvailabilityQuery query) {
        return service.checkHotelAvailability(query);
    }

}
