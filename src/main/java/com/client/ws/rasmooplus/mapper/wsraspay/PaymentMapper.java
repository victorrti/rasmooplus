package com.client.ws.rasmooplus.mapper.wsraspay;

import com.client.ws.rasmooplus.dto.wsraspay.CreditCardDTO;
import com.client.ws.rasmooplus.dto.wsraspay.PaymentDTO;

public class PaymentMapper {
    public static PaymentDTO build(String costumerId, String orderId, CreditCardDTO dto){
        return PaymentDTO
                .builder()
                .costumerId(costumerId)
                .orderId(orderId)
                .creditCard(dto)
                .build();
    }
}
