package com.yetistudios.rsww.rswwworld.command.service;

import com.yetistudios.rsww.rswwworld.RswwHotelApplication;
import com.yetistudios.rsww.rswwworld.command.dto.ReserveHotelRequest;
import com.yetistudios.rsww.rswwworld.command.event.HotelOccupationDeltaEvent;
import com.yetistudios.rsww.rswwworld.command.exception.HotelDoesNotExistException;
import com.yetistudios.rsww.rswwworld.command.exception.HotelUnavailableException;
import com.yetistudios.rsww.rswwworld.command.repository.HotelOccupationDeltaEventRepository;
import com.yetistudios.rsww.rswwworld.query.repository.HotelRepository;
import com.yetistudios.rsww.rswwworld.query.service.HotelOccupationQueryService;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HotelOccupationCommandService {

    private final Logger logger = LoggerFactory.getLogger(RswwHotelApplication.class);

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private HotelOccupationQueryService occupationQueryService;

    @Autowired
    private HotelOccupationDeltaEventRepository eventRepository;


    public void bookRooms(ReserveHotelRequest request) {
        if(!hotelRepository.existsByCode(request.hotelCode)) {
            throw new HotelDoesNotExistException();
        }
        if(!occupationQueryService.checkHotelAvailability(request)) {
            throw new HotelUnavailableException();
        }

        var maxOccupation = occupationQueryService.getMaxOccupationDuring(request.hotelCode, request.timestampBegin, request.timestampEnd).get();
        logger.info(String.format("hotel %s max availability: %d, %d, %d", request.hotelCode, maxOccupation.takenSingleRooms, maxOccupation.takenDoubleRooms, maxOccupation.takenTripleRooms));

        HotelOccupationDeltaEvent checkInEvent = HotelOccupationDeltaEvent.builder()
                ._id(new ObjectId())
                .hotelCode(request.hotelCode)
                .timestamp(request.timestampBegin)
                .deltaSingleRooms(request.numSingleRooms)
                .deltaDoubleRooms(request.numDoubleRooms)
                .deltaTripleRooms(request.numTripleRooms)
                .build();

        HotelOccupationDeltaEvent checkOutEvent = HotelOccupationDeltaEvent.builder()
                ._id(new ObjectId())
                .hotelCode(request.hotelCode)
                .timestamp(request.timestampEnd)
                .deltaSingleRooms(-request.numSingleRooms)
                .deltaDoubleRooms(-request.numDoubleRooms)
                .deltaTripleRooms(-request.numTripleRooms)
                .build();

        eventRepository.insert(checkInEvent);
        eventRepository.insert(checkOutEvent);
    }

}
