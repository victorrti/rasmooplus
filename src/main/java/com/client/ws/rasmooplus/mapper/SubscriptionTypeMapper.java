package com.client.ws.rasmooplus.mapper;

import com.client.ws.rasmooplus.Model.jpa.SubscriptionType;
import com.client.ws.rasmooplus.dto.SubscriptionTypeDTO;

public class SubscriptionTypeMapper {
    public static SubscriptionType fromDtoToEntity(SubscriptionTypeDTO dto){
        return SubscriptionType.builder()
                .id(dto.getId())
                .accessMonths(dto.getAccessMonths())
                .price(dto.getPrice())
                .name(dto.getName())
                .productKey(dto.getProductKey())
                .build();
    }
}
