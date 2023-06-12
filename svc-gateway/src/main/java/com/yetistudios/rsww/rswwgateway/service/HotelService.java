package com.yetistudios.rsww.rswwgateway.service;

import com.yetistudios.rsww.common.dto.HotelAvailabilityVector;
import com.yetistudios.rsww.common.dto.HotelDocument;
import com.yetistudios.rsww.common.messages.query.CheckHotelAvailabilityQuery;
import com.yetistudios.rsww.common.messages.query.GetHotelInfoQuery;
import com.yetistudios.rsww.rswwgateway.exception.NotFoundException;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class HotelService {
    @Autowired
    private QueryGateway queryGateway;

    public HotelDocument getHotel(String hotelCode) {
        try {
            return queryGateway
                    .query(
                            GetHotelInfoQuery.builder().hotelCode(hotelCode).build(),
                            ResponseTypes.optionalInstanceOf(HotelDocument.class))
                    .get(30, TimeUnit.SECONDS)
                    .orElseThrow(() -> new NotFoundException("no such hotel: " + hotelCode));
        } catch (Exception e) {
            throw new RuntimeException("cannot query hotel " + hotelCode + ": hotel service could not be reached");
        }

    }
}
