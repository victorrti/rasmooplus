package com.client.ws.rasmooplus.service;

import com.client.ws.rasmooplus.Model.jpa.UserCredentials;

public interface UserDetailsService {

    UserCredentials loadUserByUsernameAndPass(String username, String pass);
}
