package com.client.ws.rasmooplus.service;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;


public interface TokenService {
    String getToken(Long  id);

    Boolean isValid(String token);

    Long getUserId(String token);
}
