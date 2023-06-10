package com.yetistudios.rsww.rswwhotel.query.rest;

import com.yetistudios.rsww.common.dto.HotelSummary;
import com.yetistudios.rsww.common.messages.query.CheckHotelAvailabilityQuery;
import com.yetistudios.rsww.common.messages.query.GetRandomHotelQuery;
import com.yetistudios.rsww.rswwhotel.query.HotelMiscQueryHandler;
import com.yetistudios.rsww.rswwhotel.query.repository.HotelRepository;
import com.yetistudios.rsww.rswwhotel.query.service.HotelOccupationQueryService;
import lombok.SneakyThrows;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("hotel-availability")
public class HotelOccupationQueryRestController {
    @Autowired
    private HotelOccupationQueryService queryService;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private HotelMiscQueryHandler hotelMiscQueryHandler;

    @Autowired
    private QueryGateway queryGateway;

    @SneakyThrows
    @PostMapping("/check")
    public boolean checkReservationAvailability(@RequestBody CheckHotelAvailabilityQuery request) {
        return queryGateway.query(request, Boolean.class).get(1, TimeUnit.SECONDS);
        //return queryService.checkHotelAvailability(request);
    }

    @GetMapping("/random-hotel")
    public HotelSummary getRandomHotel() {
        return hotelMiscQueryHandler.handle(GetRandomHotelQuery.builder().country("any").build()).orElseThrow();
    }

    @GetMapping("/random-hotel-by")
    public HotelSummary getRandomHotel(@RequestParam("country") String country) {
        return hotelMiscQueryHandler.handle(GetRandomHotelQuery.builder().country(country).build()).orElseThrow();
    }
}
