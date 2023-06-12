package com.yetistudios.rsww.touroperator.query.porjection;

import com.yetistudios.rsww.common.dto.ListUpdateOfferHistory;
import com.yetistudios.rsww.common.messages.entity.UpdateOfferHistory;
import com.yetistudios.rsww.common.messages.query.GetOfferHistoriesQuery;
import com.yetistudios.rsww.common.messages.query.GetOfferQuery;
import com.yetistudios.rsww.common.dto.OfferListDto;
import com.yetistudios.rsww.touroperator.query.repository.OfferRepository;
import com.yetistudios.rsww.common.messages.entity.Offer;
import com.yetistudios.rsww.common.messages.query.GetOffersQuery;
import com.yetistudios.rsww.touroperator.query.repository.UpdateOfferHistoryRepository;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OfferProjection {
    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private UpdateOfferHistoryRepository updateOfferHistoryRepository;

    @QueryHandler
    public OfferListDto handle(GetOffersQuery getOffersQuery){
        PageRequest pageRequest = PageRequest.of(getOffersQuery.getPage(), getOffersQuery.getPageSize());
        List<Offer> offers = offerRepository.findAll();
        return new OfferListDto(offers);
    }

    @QueryHandler
    public Offer handle(GetOfferQuery query){
        return offerRepository.findById(query.getOfferId()).get();
    }

    @QueryHandler
    public ListUpdateOfferHistory handle(GetOfferHistoriesQuery query){
        List<UpdateOfferHistory> list = updateOfferHistoryRepository.findTop10ByOrderByTimeOfUpdateDesc();
        ListUpdateOfferHistory updateOfferHistory = new ListUpdateOfferHistory(list);
        return updateOfferHistory;
    }
}
