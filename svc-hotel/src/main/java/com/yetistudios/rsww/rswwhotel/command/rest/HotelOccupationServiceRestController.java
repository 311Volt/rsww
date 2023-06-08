package com.yetistudios.rsww.rswwhotel.command.rest;

import com.yetistudios.rsww.messages.command.ReserveHotelCommand;
import com.yetistudios.rsww.messages.query.CheckHotelAvailabilityQuery;
import com.yetistudios.rsww.rswwhotel.command.service.HotelOccupationCommandService;
import com.yetistudios.rsww.rswwhotel.query.service.HotelOccupationQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hotel-availability")
public class HotelOccupationServiceRestController {

    @Autowired
    private HotelOccupationCommandService commandService;

    @PostMapping("/book")
    public void bookHotel(@RequestBody ReserveHotelCommand request) {
        commandService.bookRooms(request);
    }

}
