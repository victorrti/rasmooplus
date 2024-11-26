package com.client.ws.rasmooplus.service.impl;

import com.client.ws.rasmooplus.component.HttpComponent;
import com.client.ws.rasmooplus.dto.KeycloakOauthDto;
import com.client.ws.rasmooplus.dto.LoginDto;
import com.client.ws.rasmooplus.dto.TokenDto;
import com.client.ws.rasmooplus.exception.BadRequestException;
import com.client.ws.rasmooplus.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
@Service
public class AutheticationServiceImpl implements AuthenticationService {

    private static final String REFRESH_TOKEN = "refresh_token";
    private static final String PASSWORD = "password";


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
        try {
            MultiValueMap<String, String> keyCloakOAuth = KeycloakOauthDto.builder()
                    .clientId(dto.getClienteId())
                    .clientSecret(dto.getClienteSecret())
                    .password(dto.getPassword())
                    .username(dto.getUsername())
                    .refreshToken(dto.getRefreshToken())
                    .grantType(dto.getGrantType())
                    .build();
            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(keyCloakOAuth, httpComponent.httpHeaders());
            System.out.println(request.getBody());
            ResponseEntity<String> response = httpComponent.restTemplate().postForEntity(keycloakUri + "/protocol/openid-connect/token", request, String.class);

            return response.getBody();
        }catch(Exception e){
            throw new BadRequestException("Erro ao formar token - "+e.getMessage());
        }
    }

}
