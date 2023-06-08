package com.yetistudios.rsww.rswwhotel.query.rest;

import com.yetistudios.rsww.messages.query.CheckHotelAvailabilityQuery;
import com.yetistudios.rsww.rswwhotel.query.service.HotelOccupationQueryService;
import lombok.SneakyThrows;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("hotel-availability")
public class HotelOccupationQueryRestController {
    @Autowired
    private HotelOccupationQueryService queryService;

    @Autowired
    private QueryGateway queryGateway;

    @SneakyThrows
    @PostMapping("/check")
    public boolean checkReservationAvailability(@RequestBody CheckHotelAvailabilityQuery request) {
        return queryGateway.query(request, Boolean.class).get(1, TimeUnit.SECONDS);
        //return queryService.checkHotelAvailability(request);
    }

}
