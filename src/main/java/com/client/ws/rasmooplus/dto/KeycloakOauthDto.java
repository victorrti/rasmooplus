package com.client.ws.rasmooplus.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class KeycloakOauthDto {
    private final MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
    public static KeycloakOauthDto builder(){
        return new KeycloakOauthDto();
    }

    public KeycloakOauthDto clientId(String clientId){
        params.add("client_id",clientId);
        return this;
    }
    public KeycloakOauthDto username(String username){
        params.add("username",username);
        return this;
    }
    public KeycloakOauthDto password(String paswword){
        params.add("password",paswword);
        return this;
    }
    public KeycloakOauthDto clientSecret(String clientSecret){
        params.add("client_secret",clientSecret);
        return this;
    }
    public KeycloakOauthDto grantType(String grantType){
        params.add("grant_type",grantType);
        return this;
    }
    public KeycloakOauthDto refreshToken(String refreshToken){
        params.add("refresh_token",refreshToken);
        return this;
    }

    public MultiValueMap<String,String> build(){
        return params;
    }

    /*private String cliente_id;
    private String username;
    private String grant_type;
    private String password;
    private String client_secret;
    private String refresh_token;*/



}
