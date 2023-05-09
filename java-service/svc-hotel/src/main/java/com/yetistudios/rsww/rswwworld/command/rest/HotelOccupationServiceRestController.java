package com.yetistudios.rsww.rswwworld.command.rest;

import com.yetistudios.rsww.rswwworld.command.dto.ReserveHotelRequest;
import com.yetistudios.rsww.rswwworld.command.service.HotelOccupationCommandService;
import com.yetistudios.rsww.rswwworld.query.service.HotelOccupationQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hotel-availability")
public class HotelOccupationServiceRestController {

    @Autowired
    private HotelOccupationQueryService queryService;

    @Autowired
    private HotelOccupationCommandService commandService;

    @PostMapping("/check")
    public boolean checkReservationAvailability(@RequestBody ReserveHotelRequest request) {
        return queryService.checkHotelAvailability(request);
    }

    @PostMapping("/book")
    public void bookHotel(@RequestBody ReserveHotelRequest request) {
        commandService.bookRooms(request);
    }

}
