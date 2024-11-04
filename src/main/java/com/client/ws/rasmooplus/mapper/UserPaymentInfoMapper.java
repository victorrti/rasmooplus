package com.client.ws.rasmooplus.mapper;

import com.client.ws.rasmooplus.Model.jpa.User;
import com.client.ws.rasmooplus.Model.jpa.UserPaymentInfo;
import com.client.ws.rasmooplus.dto.UserPaymentInfoDto;

public class UserPaymentInfoMapper {
    public static UserPaymentInfo fromDtoToEntity(UserPaymentInfoDto dto, User user){
        return UserPaymentInfo.builder()
                .id(dto.getId())
                .dtPayment(dto.getDtPayment())
                .cardExpirationMonth(dto.getCardExpirationMonth())
                .cardExpirationYear(dto.getCardExpirationYear())
                .cardNumber(dto.getCardNumber())
                .cardSecurityCode(dto.getCardSecurityCode())
                .user(user)
                .build();
    }
}
