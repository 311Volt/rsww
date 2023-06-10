package com.yetistudios.rsww.touroperator.query.controller;

import com.yetistudios.rsww.messages.entity.Offer;
import com.yetistudios.rsww.touroperator.query.dto.DetailedOfferDto;
import com.yetistudios.rsww.touroperator.query.queries.GetOfferDetailedQuery;
import com.yetistudios.rsww.touroperator.query.queries.GetOffersQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/offer")
public class OfferQuertController {

    private QueryGateway queryGateway;

    public OfferQuertController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    @CrossOrigin
    @GetMapping
    public List<Offer> getAllOffers(@RequestParam(defaultValue = "") String destination, @RequestParam(defaultValue = "") String departure, @RequestParam(defaultValue = "1900-01-01") LocalDate startDate, @RequestParam(defaultValue = "1") int people, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int pageSize) {
        GetOffersQuery getOffersQuery = GetOffersQuery.builder()
                .destination(destination)
                .departure(departure)
                .startDate(startDate)
                .people(people)
                .page(page)
                .pageSize(pageSize)
                .build();

        return queryGateway.query(getOffersQuery, ResponseTypes.multipleInstancesOf(Offer.class)).join();
    }

    //not working TODO
    @GetMapping("/detailed")
    public DetailedOfferDto getOffer(@RequestParam("id") String offerId) {
        GetOfferDetailedQuery getOfferDetailedQuery = new GetOfferDetailedQuery(offerId);

        return queryGateway.query(getOfferDetailedQuery, ResponseTypes.instanceOf(DetailedOfferDto.class)).join();
    }
}
