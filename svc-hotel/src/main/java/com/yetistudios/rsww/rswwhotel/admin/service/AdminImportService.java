package com.yetistudios.rsww.rswwhotel.admin.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yetistudios.rsww.common.dto.HotelDocument;
import com.yetistudios.rsww.rswwhotel.query.entity.Hotel;
import com.yetistudios.rsww.rswwhotel.query.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("!test")
public class AdminImportService {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private HotelRepository hotelRepository;

    public void importHotel(HotelDocument hotelDocument) {
        Hotel hotel = Hotel.ofDocument(hotelDocument);
        if(hotelRepository.existsByCode(hotel.code)) {
            hotelRepository.deleteAllByCode(hotel.code);
        }
        hotelRepository.save(hotel);
    }

}
