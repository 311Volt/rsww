package com.yetistudios.rsww.rswwworld.command.rest;

import com.yetistudios.rsww.rswwworld.command.dto.ReserveHotelRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hotel-availability")
public class HotelAvailabilityServiceRestController {

    @PostMapping("/check")
    public void checkReservationAvailability(ReserveHotelRequest request) {

    }

    @PostMapping("/book")
    public void bookHotel(ReserveHotelRequest request) {

    }

}
