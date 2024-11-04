package com.client.ws.rasmooplus.service.impl;

import com.client.ws.rasmooplus.Model.jpa.UserCredentials;
import com.client.ws.rasmooplus.exception.BadRequestException;
import com.client.ws.rasmooplus.exception.NotFoundException;
import com.client.ws.rasmooplus.repository.jpa.UserDetailsRepository;
import com.client.ws.rasmooplus.service.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserDetailsRepository userDetailsRepository;
    @Override
    public UserCredentials loadUserByUsernameAndPass(String username, String pass) {
        var userCredentialsOpt = userDetailsRepository.findByUsername(username);
        if(userCredentialsOpt.isEmpty()){
            throw  new NotFoundException("Usuario ou senha invalido");
        }
        UserCredentials userCredentials = userCredentialsOpt.get();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if(encoder.matches(pass,userCredentials.getPassword())){
            return userCredentials;
        }
        throw  new BadRequestException("Usuario ou senha invalido");
    }
}
