package com.client.ws.rasmooplus.service;

import com.client.ws.rasmooplus.Model.jpa.UserCredentials;
import com.client.ws.rasmooplus.Model.redis.UserRecoveryCode;
import com.client.ws.rasmooplus.dto.UserDetailsDto;

public interface UserDetailsService {
    void sendRecoveryCode(String email);

    Boolean recoveryCodeIsValid(String recoveryCode,String email);

    UserCredentials loadUserByUsernameAndPass(String username, String pass);

    void updatePasswordByRecoveryCode(UserDetailsDto userDetailsDto);
}
