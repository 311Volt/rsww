package com.yetistudios.rsww.touroperator.cmd.generator;

import com.yetistudios.rsww.common.dto.FlightPair;
import com.yetistudios.rsww.common.dto.HotelSummary;
import com.yetistudios.rsww.common.messages.query.*;
import com.yetistudios.rsww.touroperator.cmd.entity.Flight;
import com.yetistudios.rsww.touroperator.cmd.entity.Hotel;
import com.yetistudios.rsww.touroperator.cmd.entity.Offer;
import com.yetistudios.rsww.touroperator.cmd.repository.OfferRepository;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class OfferGenerator {

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private QueryGateway queryGateway;

    private final static Random random = new Random();
    private final static int MAX_CONSECUTIVE_FAILED_ATTEMPTS = 40;

    @SneakyThrows
    public Offer tryGenerateOffer() {

        int durationDays = random.nextInt(3, 7);

        LocalDateTime base = LocalDateTime.of(2023, 6, 1, 16, 0);
        LocalDateTime t1 = base.plus(random.nextInt(120), ChronoUnit.DAYS);
        LocalDateTime t2 = t1.plus(durationDays, ChronoUnit.DAYS);

        Long t1unix = t1.toEpochSecond(ZoneOffset.UTC);
        Long t2unix = t1.toEpochSecond(ZoneOffset.UTC);

        HotelSummary hotel = queryGateway
                .query(
                        GetRandomHotelQuery.builder().country("any").build(),
                        ResponseTypes.optionalInstanceOf(HotelSummary.class))
                .get(1, TimeUnit.SECONDS)
                .orElseThrow(() -> new RuntimeException("no hotel found"));

        String outboundAirport = "WAW";
        String hotelAirport = hotel.airportCode;

        FindBestFlightPairQuery findBestFlightPairQuery = FindBestFlightPairQuery.builder()
                .latestAcceptableOutboundArrival(t1unix)
                .earliestAcceptableReturnDeparture(t2unix)
                .outboundDepartureAirportCode(outboundAirport)
                .outboundArrivalAirportCode(hotelAirport)
                .build();

        FlightPair flightPair = (queryGateway
                .query(findBestFlightPairQuery, ResponseTypes.optionalInstanceOf(FlightPair.class))
                .get(1, TimeUnit.SECONDS))
                .orElseThrow(() -> new RuntimeException("no flight pair found"));

        CheckHotelAvailabilityQuery hotelAvailabilityQuery = CheckHotelAvailabilityQuery.builder()
                .numSingleRooms(2)
                .numDoubleRooms(1)
                .numTripleRooms(0)
                .timestampBegin(t1unix)
                .timestampEnd(t2unix)
                .hotelCode(hotel.code)
                .build();

        CheckFlightAvailabilityQuery flightAvailabilityQuery1 = CheckFlightAvailabilityQuery.builder()
                .numSeats(2)
                .flightNumber(flightPair.outboundFlight.flightNumber)
                .build();

        CheckFlightAvailabilityQuery flightAvailabilityQuery2 = CheckFlightAvailabilityQuery.builder()
                .numSeats(2)
                .flightNumber(flightPair.outboundFlight.flightNumber)
                .build();

        Boolean isHotelAvailable = queryGateway.query(hotelAvailabilityQuery, Boolean.class).get(1, TimeUnit.SECONDS);
        Boolean isFlight1Available = queryGateway.query(flightAvailabilityQuery1, Boolean.class).get(1, TimeUnit.SECONDS);
        Boolean isFlight2Available = queryGateway.query(flightAvailabilityQuery2, Boolean.class).get(1, TimeUnit.SECONDS);

        if(!isHotelAvailable) throw new RuntimeException("hotel unavailable");
        if(!isFlight1Available) throw new RuntimeException("outbound flight unavailable");
        if(!isFlight2Available) throw new RuntimeException("return flight unavailable");

        GetFlightBookingPriceQuery flightPriceQuery1 = GetFlightBookingPriceQuery.builder()
                .flightNumber(flightPair.outboundFlight.flightNumber)
                .numSeats(2)
                .build();

        GetFlightBookingPriceQuery flightPriceQuery2 = GetFlightBookingPriceQuery.builder()
                .flightNumber(flightPair.returnFlight.flightNumber)
                .numSeats(2)
                .build();

        List<String> twoAdultsBirthDates = List.of("1986-04-26", "1986-04-26");

        GetHotelBookingPriceQuery hotelPriceQuery = GetHotelBookingPriceQuery.builder()
                .birthDates(twoAdultsBirthDates)
                .hotelCode(hotel.code)
                .timestampBegin(t1unix)
                .timestampEnd(t2unix)
                .build();

        Double flightPrice1 = queryGateway
                .query(flightPriceQuery1, ResponseTypes.optionalInstanceOf(Double.class))
                .get(1, TimeUnit.SECONDS)
                .orElseThrow();
        Double flightPrice2 = queryGateway
                .query(flightPriceQuery2, ResponseTypes.optionalInstanceOf(Double.class))
                .get(1, TimeUnit.SECONDS)
                .orElseThrow();
        Double hotelPrice = queryGateway
                .query(hotelPriceQuery, ResponseTypes.optionalInstanceOf(Double.class))
                .get(1, TimeUnit.SECONDS)
                .orElseThrow();

        Double finalPrice = flightPrice1 + flightPrice2 + hotelPrice;


        return Offer.builder()
                .id(UUID.randomUUID().toString())
                .hotel(Hotel.ofSummary(hotel))
                .price(finalPrice)
                .flights(List.of(
                        Flight.ofDocument(flightPair.outboundFlight),
                        Flight.ofDocument(flightPair.returnFlight)))
                .numberOfOffers(3)
                .startDate(t1)
                .endDate(t2)
                .build();
    }

    public Offer generateOffer() {
        int fails = 0;
        while(true) {
            try {
                Offer offer = tryGenerateOffer();
                fails = 0;
                return offer;
            } catch (Exception e) {
                fails++;
                log.info("cannot generate offer: {} (attempt {} / {})", e.getMessage(), fails, MAX_CONSECUTIVE_FAILED_ATTEMPTS);
                if(fails >= MAX_CONSECUTIVE_FAILED_ATTEMPTS) {
                    e.printStackTrace();
                    throw e;
                }
            }
        }
    }

    public List<Offer> generateOffers(int numOffers) {
        List<Offer> result = new ArrayList<>();
        for(int i=0; i<numOffers; i++) {
            log.info("generating offer #{}...", i);
            result.add(generateOffer());
        }
        return result;
    }
}
