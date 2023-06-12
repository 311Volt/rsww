package com.yetistudios.rsww.rswwgateway.controller;

import com.yetistudios.rsww.common.dto.HotelAvailabilityVector;
import com.yetistudios.rsww.common.dto.HotelDocument;
import com.yetistudios.rsww.common.messages.query.GetHotelInfoQuery;
import com.yetistudios.rsww.rswwgateway.exception.NotFoundException;
import com.yetistudios.rsww.rswwgateway.service.HotelService;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/hotel")
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @GetMapping("/{id}")
    public HotelDocument getHotel(@PathVariable("id") String hotelCode) {
        return hotelService.getHotel(hotelCode);
    }

}
