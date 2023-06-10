package com.yetistudios.rsww.rswwhotel.query;

import com.yetistudios.rsww.messages.query.GetHotelBookingPriceQuery;
import com.yetistudios.rsww.rswwhotel.query.entity.Hotel;
import com.yetistudios.rsww.rswwhotel.query.repository.HotelRepository;
import com.yetistudios.rsww.rswwhotel.query.service.HotelOccupationQueryService;
import lombok.SneakyThrows;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.TimeZone;

@Component
public class HotelPriceQueryHandler {

    @Autowired
    private HotelOccupationQueryService occupationQueryService;

    @Autowired
    private HotelRepository hotelRepository;

    @SneakyThrows
    private static long dateStrToUnix(String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        return format.parse(date).getTime() / 1000L;
    }


    public Double priceForPerson(Hotel hotel, int nights, String birthDate) {
        Instant instBirthDate = Instant.ofEpochSecond(dateStrToUnix(birthDate));
        int age = (int) (Duration.between(instBirthDate, Instant.now()).toDays() / 365);
        if(hotel.ageRange0.containsAge(age)) {
            return nights * hotel.ageRange0.pricePerNight;
        } else if(hotel.ageRange1.containsAge(age)) {
            return nights * hotel.ageRange1.pricePerNight;
        } else if(hotel.ageRange2.containsAge(age)) {
            return nights * hotel.ageRange2.pricePerNight;
        }
        return 0.0;
    }

    @QueryHandler
    public Optional<Double> handle(GetHotelBookingPriceQuery query) {
        Optional<Hotel> hotel = hotelRepository.findByCode(query.hotelCode);
        if(hotel.isEmpty()) {
            return Optional.empty();
        }

        Instant d1 = Date.from(Instant.ofEpochSecond(query.timestampBegin)).toInstant();
        Instant d2 = Date.from(Instant.ofEpochSecond(query.timestampEnd)).toInstant();
        int nights = (int)Duration.between(d1, d2).toDays();

        double result = query.birthDates
                .stream()
                .reduce(
                        0.0,
                        (partial, date) -> partial + priceForPerson(hotel.get(), nights, date),
                        Double::sum
                );
        return Optional.of(result);
    }

}
