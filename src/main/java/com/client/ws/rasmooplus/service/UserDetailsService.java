package com.client.ws.rasmooplus.service;

import com.client.ws.rasmooplus.Model.jpa.UserCredentials;

public interface UserDetailsService {
    void sendRecoveryCode(String email);

    Boolean recoveryCodeIsValid(String recoveryCode,String email);

    UserCredentials loadUserByUsernameAndPass(String username, String pass);
}
