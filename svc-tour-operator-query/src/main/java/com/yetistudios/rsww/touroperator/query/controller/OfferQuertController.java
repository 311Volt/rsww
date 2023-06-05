package com.yetistudios.rsww.touroperator.query.controller;

import com.yetistudios.rsww.touroperator.query.entity.Offer;
import com.yetistudios.rsww.touroperator.query.dto.DetailedOfferDto;
import com.yetistudios.rsww.touroperator.query.queries.GetOfferDetailedQuery;
import com.yetistudios.rsww.touroperator.query.queries.GetOffersQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/offer")
public class OfferQuertController {

    private QueryGateway queryGateway;

    public OfferQuertController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    @GetMapping
    public List<Offer> getAllOffers() {
        GetOffersQuery getOffersQuery = new GetOffersQuery();

        return queryGateway.query(getOffersQuery, ResponseTypes.multipleInstancesOf(Offer.class)).join();
    }

    //not working TODO
    @GetMapping("/detailed")
    public DetailedOfferDto getOffer(@RequestParam("id") String offerId) {
        GetOfferDetailedQuery getOfferDetailedQuery = new GetOfferDetailedQuery(offerId);

        return queryGateway.query(getOfferDetailedQuery, ResponseTypes.instanceOf(DetailedOfferDto.class)).join();
    }
}
