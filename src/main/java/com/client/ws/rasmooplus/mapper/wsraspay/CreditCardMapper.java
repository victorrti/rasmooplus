package com.client.ws.rasmooplus.mapper.wsraspay;

import com.client.ws.rasmooplus.dto.PaymentProcessDTO;
import com.client.ws.rasmooplus.dto.UserPaymentInfoDto;
import com.client.ws.rasmooplus.dto.wsraspay.CreditCardDTO;

public class CreditCardMapper {
    public static CreditCardDTO build(UserPaymentInfoDto dto, String documentoNumber){
        return CreditCardDTO.builder()
                .cvv(Long.parseLong(dto.getCardSecurityCode()))
                .documentNumber(documentoNumber)
                .month(dto.getCardExpirationMonth())
                .year(dto.getCardExpirationYear())
                .number(dto.getCardNumber())
                .installments(dto.getInstallments())


                .build();
    }
}
