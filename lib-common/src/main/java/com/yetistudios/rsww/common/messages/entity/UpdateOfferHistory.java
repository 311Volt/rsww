package com.yetistudios.rsww.common.messages.entity;

import com.yetistudios.rsww.common.messages.event.UpdateOfferEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document
public class UpdateOfferHistory {
    @Id
    private String id;
    private UpdateOfferEvent oldValues;
    private UpdateOfferEvent newValues;
    private LocalDateTime timeOfUpdate;
}
