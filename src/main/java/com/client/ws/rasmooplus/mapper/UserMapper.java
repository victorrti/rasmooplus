package com.client.ws.rasmooplus.mapper;

import com.client.ws.rasmooplus.Model.SubscriptionType;
import com.client.ws.rasmooplus.Model.User;
import com.client.ws.rasmooplus.Model.UserType;
import com.client.ws.rasmooplus.dto.UserDto;

public class UserMapper {
    public static User fromDtoToEntity(UserDto userDto, UserType userType, SubscriptionType subscriptionType){
        return User.builder()
                .id(userDto.getId())
                .cpf(userDto.getCpf())
                .phone(userDto.getPhone())
                .email(userDto.getEmail())
                .dtSubscription(userDto.getDtSubscription())
                .dtExpiration(userDto.getDtExpiration())
                .nome(userDto.getNome())
                .userType(userType)
                .subscriptionType(subscriptionType)
                .build();
    }
}
