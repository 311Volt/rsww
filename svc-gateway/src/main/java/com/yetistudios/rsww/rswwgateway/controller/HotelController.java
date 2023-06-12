package com.yetistudios.rsww.rswwgateway.controller;

import com.yetistudios.rsww.common.dto.HotelAvailabilityVector;
import com.yetistudios.rsww.common.dto.HotelDocument;
import com.yetistudios.rsww.rswwgateway.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hotel")
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @GetMapping("/{id}")
    public HotelDocument getHotel(@PathVariable("id") String hotelCode) {
        return hotelService.getHotel(hotelCode);
    }

    @GetMapping("/{id}/availability")
    public HotelAvailabilityVector getHotelAvailability(
            @PathVariable("id") String hotelCode,
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate) {
        return hotelService.getHotelAvailability(hotelCode, startDate, endDate);
    }

}
