package com.yetistudios.rsww.rswwhotel.query;

import com.yetistudios.rsww.common.dto.HotelDocument;
import com.yetistudios.rsww.common.dto.HotelSummary;
import com.yetistudios.rsww.common.messages.query.GetFlightInfoQuery;
import com.yetistudios.rsww.common.messages.query.GetHotelInfoQuery;
import com.yetistudios.rsww.common.messages.query.GetRandomHotelQuery;
import com.yetistudios.rsww.rswwhotel.query.entity.Hotel;
import com.yetistudios.rsww.rswwhotel.query.repository.HotelRepository;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public class HotelMiscQueryHandler {

    @Autowired
    private HotelRepository hotelRepository;

    @QueryHandler
    public Optional<HotelSummary> handle(GetRandomHotelQuery query) {
        try {
            if (query.country.equals("any")) {
                return hotelRepository
                        .findRandom().getMappedResults()
                        .stream().findFirst().map(Hotel::toSummary);
            } else {
                return hotelRepository
                        .findRandomByCountry(query.country).getMappedResults()
                        .stream().findFirst().map(Hotel::toSummary);
            }
        } catch (Throwable e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }


    @QueryHandler
    public Optional<HotelDocument> handle(GetHotelInfoQuery query) {
        return hotelRepository.findByCode(query.hotelCode).map(Hotel::toDocument);
    }
}
