package com.client.ws.rasmooplus.service.impl;

import com.client.ws.rasmooplus.component.HttpComponent;
import com.client.ws.rasmooplus.dto.LoginDto;
import com.client.ws.rasmooplus.dto.TokenDto;
import com.client.ws.rasmooplus.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class AutheticationServiceImpl implements AuthenticationService {

    @Value("${keycloak.auth.server-uri}")
    private String keycloakUri;
    @Value("${keycloak.credentials.client-id}")
    private String clientId;
    @Value("${keycloak.credentials.client-secret}")
    private String clientSecret;
    @Value("${keycloak.credentials.grant-type}")
    private String grantType;

    @Autowired
    private HttpComponent httpComponent;
    @Override
    public String auth(LoginDto dto) {
        return "teste";
    }
}
