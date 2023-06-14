package com.yetistudios.rsww.touroperator.cmd.generator;

import com.yetistudios.rsww.common.dto.*;
import com.yetistudios.rsww.common.messages.query.*;
import com.yetistudios.rsww.common.messages.entity.FlightBrief;
import com.yetistudios.rsww.common.messages.entity.FlightBriefPair;
import com.yetistudios.rsww.common.messages.entity.HotelBrief;
import com.yetistudios.rsww.common.messages.entity.Offer;
import com.yetistudios.rsww.common.messages.command.CreateOfferCommand;
import com.yetistudios.rsww.common.util.RandomUtil;
import com.yetistudios.rsww.touroperator.cmd.repository.OfferRepository;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
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
    @Autowired
    private CommandGateway commandGateway;
    private final static int MAX_CONSECUTIVE_FAILED_ATTEMPTS = 40;
    private final static int SUGGESTED_POPULATION = 2;

    @SneakyThrows
    public Offer tryGenerateOffer() {

        int durationDays = RandomUtil.randomInt(3, 8);

        LocalDateTime base = LocalDateTime.of(2023, 6, 1, 16, 0);
        LocalDateTime t1 = base.plus(RandomUtil.randomInt(120), ChronoUnit.DAYS);
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

        FindAllViableFlightPairsQuery flightsQuery = FindAllViableFlightPairsQuery.builder()
                .latestAcceptableOutboundArrival(t1unix)
                .earliestAcceptableReturnDeparture(t2unix)
                .outboundArrivalAirportCode(hotelAirport)
                .build();

        FlightPairList flightPairsObj = (queryGateway
                .query(flightsQuery, FlightPairList.class)
                .get(1, TimeUnit.SECONDS));

        var flightPairs = flightPairsObj.flights;

        if(flightPairs.isEmpty()) {
            throw new RuntimeException("no viable flights found");
        }

        CheckHotelAvailabilityQuery hotelAvailabilityQuery = CheckHotelAvailabilityQuery.builder()
                .timestampBegin(t1unix)
                .timestampEnd(t2unix)
                .hotelCode(hotel.code)
                .build();

        HotelAvailabilityVector hotelAvailability = queryGateway
                .query(hotelAvailabilityQuery, HotelAvailabilityVector.class)
                .get(1, TimeUnit.SECONDS);

        HotelRoomVector sampleOccupation = new HotelRoomVector(SUGGESTED_POPULATION, 0, 0);

        if(!sampleOccupation.fitsInto(hotelAvailability)) {
            throw new RuntimeException("hotel is not available");
        }

        List<Integer> allFlightNumbers = flightPairs
                .stream()
                .<Integer>mapMulti((flightPair, consumer) -> {
                    consumer.accept(flightPair.outboundFlight.flightNumber);
                    consumer.accept(flightPair.returnFlight.flightNumber);
                })
                .toList();

        Double flightPrices = 0.0;

        for(Integer flightNumber: allFlightNumbers) {

            var availabilityQuery = CheckFlightAvailabilityQuery.builder()
                    .flightNumber(flightNumber)
                    .numSeats(SUGGESTED_POPULATION)
                    .build();

            var priceQuery = GetFlightBookingPriceQuery.builder()
                    .flightNumber(flightNumber)
                    .numSeats(SUGGESTED_POPULATION)
                    .build();

            Boolean isFlightAvailable = queryGateway.query(availabilityQuery, Boolean.class).get(1, TimeUnit.SECONDS);
            if(!isFlightAvailable) {
                throw new RuntimeException("flight not available");
            }

            Double flightPrice = queryGateway
                    .query(priceQuery, ResponseTypes.optionalInstanceOf(Double.class))
                    .get(1, TimeUnit.SECONDS)
                    .orElseThrow();

            flightPrices += flightPrice;
        }
        flightPrices /= flightPairsObj.flights.size();


        List<String> twoAdultsBirthDates = List.of("1986-04-26", "1986-04-26");

        GetHotelBookingPriceQuery hotelPriceQuery = GetHotelBookingPriceQuery.builder()
                .birthDates(twoAdultsBirthDates)
                .hotelCode(hotel.code)
                .timestampBegin(t1unix)
                .timestampEnd(t2unix)
                .build();

        Double hotelPrice = queryGateway
                .query(hotelPriceQuery, ResponseTypes.optionalInstanceOf(Double.class))
                .get(1, TimeUnit.SECONDS)
                .orElseThrow();


        Double finalPrice = flightPrices + hotelPrice;

        List<FlightBriefPair> flightBriefPairs = flightPairs
                .stream()
                .map(flightPair -> FlightBriefPair
                        .builder()
                        .outboundFlight(FlightBrief.ofDocument(flightPair.outboundFlight))
                        .returnFlight(FlightBrief.ofDocument(flightPair.returnFlight))
                        .build()
                )
                .toList();


        return Offer.builder()
                .id(UUID.randomUUID().toString())
                .hotelBrief(HotelBrief.ofSummary(hotel))
                .suggestedPrice(finalPrice)
                .flights(flightBriefPairs)
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
            Offer offer = generateOffer();
            result.add(offer);

            CreateOfferCommand createOfferCommand = CreateOfferCommand.builder()
                    .offerId(UUID.randomUUID().toString())
                    .hotelBrief(offer.getHotelBrief())
                    .flights(offer.getFlights())
                    .price(offer.getSuggestedPrice())
                    .numberOfOffers(offer.getNumberOfOffers())
                    .startDate(offer.getStartDate())
                    .endDate(offer.getEndDate())
                    .build();

            commandGateway.send(createOfferCommand);
        }

        return result;
    }
}
