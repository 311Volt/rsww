package com.yetistudios.rsww.rswwhotel.command.rest;

import com.yetistudios.rsww.common.messages.command.BookHotelCommand;
import com.yetistudios.rsww.rswwhotel.command.service.HotelOccupationCommandService;
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
    public void bookHotel(@RequestBody BookHotelCommand request) {
        commandService.bookRooms(request);
    }

}
