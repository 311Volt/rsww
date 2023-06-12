package com.yetistudios.rsww.common.dto;

import com.yetistudios.rsww.common.messages.entity.UpdateOfferHistory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ListUpdateOfferHistory {
    List<UpdateOfferHistory> updateOfferHistoryList;
}
