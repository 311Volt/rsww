package com.yetistudios.rsww.touroperator.cmd.query.porjection;

import com.yetistudios.rsww.touroperator.cmd.command.repository.OfferRepository;
import com.yetistudios.rsww.touroperator.cmd.entity.Offer;
import com.yetistudios.rsww.touroperator.cmd.query.queries.GetOffersQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OfferProjection {
    private OfferRepository offerRepository;

    public OfferProjection(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }

    @QueryHandler
    public List<Offer> handle(GetOffersQuery getOffersQuery){
        return offerRepository.findAll();
    }
}
