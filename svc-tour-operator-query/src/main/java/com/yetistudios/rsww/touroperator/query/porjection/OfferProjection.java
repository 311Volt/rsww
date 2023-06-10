package com.yetistudios.rsww.touroperator.query.porjection;

import com.yetistudios.rsww.messages.query.GetOfferQuery;
import com.yetistudios.rsww.touroperator.query.repository.OfferRepository;
import com.yetistudios.rsww.messages.entity.Offer;
import com.yetistudios.rsww.touroperator.query.dto.DetailedOfferDto;
import com.yetistudios.rsww.touroperator.query.queries.GetOfferDetailedQuery;
import com.yetistudios.rsww.touroperator.query.queries.GetOffersQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class OfferProjection {
    private OfferRepository offerRepository;

    public OfferProjection(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }

    @QueryHandler
    public List<Offer> handle(GetOffersQuery getOffersQuery){
        PageRequest pageRequest = PageRequest.of(getOffersQuery.getPage(), getOffersQuery.getPageSize());
        List<Offer> offers =  offerRepository.findOffersByCriteria(getOffersQuery.getDestination(), getOffersQuery.getDeparture(),getOffersQuery.getStartDate(), getOffersQuery.getPeople(), pageRequest);
        return offers;
    }

    @QueryHandler DetailedOfferDto handle(GetOfferDetailedQuery getOfferDetailedQuery){
        Optional<Offer> offerOptional = offerRepository.findById(getOfferDetailedQuery.getOfferId());
        if(offerOptional.isPresent()){
            Offer offer = offerOptional.get();

            DetailedOfferDto detailedOffer = DetailedOfferDto.builder()

                    .build();
        }

        return null;
    }

    @QueryHandler Offer handle(GetOfferQuery query){
        return offerRepository.findById(query.getOfferId()).get();
    }
}
