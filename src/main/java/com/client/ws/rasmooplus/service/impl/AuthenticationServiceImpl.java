package com.client.ws.rasmooplus.service.impl;

import com.client.ws.rasmooplus.Model.jpa.UserCredentials;
import com.client.ws.rasmooplus.dto.LoginDto;
import com.client.ws.rasmooplus.dto.TokenDto;
import com.client.ws.rasmooplus.exception.BadRequestException;
import com.client.ws.rasmooplus.service.AuthenticationService;
import com.client.ws.rasmooplus.service.TokenService;
import com.client.ws.rasmooplus.service.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private UserDetailsService userDetailsService;
    private TokenService tokenService;
    @Override
    public TokenDto auth(LoginDto dto) {
        try{
            UserCredentials userCredentials = userDetailsService.loadUserByUsernameAndPass(dto.getUsername(),dto.getPassword());
            String  token =  tokenService.getToken(userCredentials.getId());
            return TokenDto.builder().token(token).Type("Bearer").build();
        }catch(Exception e){
            throw  new BadRequestException("Erro na authenticação - "+e.getMessage());

        }

    }
}
