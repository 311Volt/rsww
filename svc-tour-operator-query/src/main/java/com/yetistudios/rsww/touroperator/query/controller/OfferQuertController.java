package com.yetistudios.rsww.touroperator.query.controller;

import com.yetistudios.rsww.common.messages.entity.Offer;
import com.yetistudios.rsww.common.messages.query.GetOfferQuery;
import com.yetistudios.rsww.common.messages.query.GetOffersQuery;
import com.yetistudios.rsww.touroperator.query.dto.OfferListDto;
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

        OfferListDto offers = queryGateway.query(
                getOffersQuery,
                ResponseTypes.instanceOf(OfferListDto.class)
        ).join();

        return offers.getOfferList();
    }

    //not working TODO
    @CrossOrigin
    @GetMapping("/detailed")
    public Offer getOffer(@RequestParam("id") String offerId) {
        GetOfferQuery getOfferQuery = new GetOfferQuery(offerId);

        return queryGateway.query(getOfferQuery, ResponseTypes.instanceOf(Offer.class)).join();
    }
}
