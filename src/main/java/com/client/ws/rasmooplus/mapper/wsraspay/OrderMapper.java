package com.client.ws.rasmooplus.mapper.wsraspay;

import com.client.ws.rasmooplus.dto.PaymentProcessDTO;
import com.client.ws.rasmooplus.dto.wsraspay.OrderDTO;

public class OrderMapper {
    public static OrderDTO build(String costumerId, PaymentProcessDTO dto){
        return OrderDTO.builder()
                .costumerId(costumerId)
                .discount(dto.getDiscount())
                .productAcronym(dto.getProductKey())
                .build();

    }
}
