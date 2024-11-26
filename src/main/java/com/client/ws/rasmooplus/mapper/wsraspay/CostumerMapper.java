package com.client.ws.rasmooplus.mapper.wsraspay;

import com.client.ws.rasmooplus.Model.jpa.User;
import com.client.ws.rasmooplus.dto.wsraspay.CostumerDTO;

public class CostumerMapper {

    public static CostumerDTO build(User user){
        var fullName = user.getName().split(" ");
        var firstName = fullName[0];
        var lastName = fullName.length > 1 ? fullName[firstName.length() -1] : "";
        return CostumerDTO.builder()
                .email(user.getEmail())
                .firstName(firstName)
                .lastName(lastName)
                .cpf(user.getCpf())
                .build();
    }
}
