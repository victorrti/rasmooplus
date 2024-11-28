package com.client.ws.rasmooplus.service.impl;

import com.client.ws.rasmooplus.Integration.MailIntegration;

import com.client.ws.rasmooplus.Model.redis.UserRecoveryCode;
import com.client.ws.rasmooplus.component.HttpComponent;
import com.client.ws.rasmooplus.dto.LoginDto;
import com.client.ws.rasmooplus.dto.UserDetailsDto;
import com.client.ws.rasmooplus.dto.oauth.UserRepresentationDto;
import com.client.ws.rasmooplus.exception.BadRequestException;
import com.client.ws.rasmooplus.exception.NotFoundException;
import com.client.ws.rasmooplus.repository.redis.UserRecoveryCodeRepository;
import com.client.ws.rasmooplus.service.AuthenticationService;
import com.client.ws.rasmooplus.service.UserDetailsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRecoveryCodeRepository userRecoveryCodeRepository;
    @Autowired
    private MailIntegration mailIntegration;
    @Value("${webservice.rasplus.redis.recovercody.timeout}")
    private String recoverTimeOut;

    @Value("${keycloak.credentials.client-id}")
    private String clientId;
    @Value("${keycloak.credentials.client-secret}")
    private String clientSecret;
    @Value("${keycloak.credentials.grant-type}")
    private String grandType;
    @Value("${keycloak.auth.server-uri}")
    private String keycloakUri;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    HttpComponent httpComponent;







    @Override
    public void sendRecoveryCode(String email) {
      /*  UserRecoveryCode userRecoveryCode;
        var userRecoveryCodeOpt = userRecoveryCodeRepository.findByEmail(email);
        String code = String.format("%04d", new Random().nextInt(10000));
        if(userRecoveryCodeOpt.isEmpty()){
            var userDetailsOpt = userDetailsRepository.findByUsername(email);
            if(userDetailsOpt.isEmpty()){
                throw new NotFoundException("Usuario não encontrado.");
            }
            userRecoveryCode = new UserRecoveryCode();
            userRecoveryCode.setEmail(email);

        }else{
            userRecoveryCode = userRecoveryCodeOpt.get();
        }
        userRecoveryCode.setCode(code);
        userRecoveryCode.setLocalDateTime(LocalDateTime.now());

        userRecoveryCodeRepository.save(userRecoveryCode);
        mailIntegration.send(email,"Codigo de recuperação de conta "+code,"Codigo de recuperação de conta " +code);
        */

    }

    @Override
     public Boolean recoveryCodeIsValid(String recoveryCode, String email) {
       var userRecoveryCodeOpt = userRecoveryCodeRepository.findByEmail(email);
        if(userRecoveryCodeOpt.isEmpty()){
            throw new NotFoundException("Usuario não encontrado.");
        }
        UserRecoveryCode userRecoveryCode = userRecoveryCodeOpt.get();
        LocalDateTime timeout = userRecoveryCode.getLocalDateTime().plusMinutes(Long.parseLong(recoverTimeOut));
        LocalDateTime now = LocalDateTime.now();
        if(recoveryCode.equals(userRecoveryCode.getCode()) && now.isBefore(timeout)){
            return true;
        }
        return false;
    }
    @Override
    public void updatePasswordByRecoveryCode(UserDetailsDto userDetailsDto) {
        /*if(recoveryCodeIsValid(userDetailsDto.getRecoveryCode(),userDetailsDto.getEmail())){
            var userDetailsOpt = userDetailsRepository.findByUsername(userDetailsDto.getEmail());
            UserCredentials userCredentials = userDetailsOpt.get();
            userCredentials.setPassword(PasswordUtils.encode(userDetailsDto.getPassword()) );
            userDetailsRepository.save(userCredentials);

        }*/

    }

    @Override
    public void createAuthUser(UserRepresentationDto userRepresentationDto) {
        try {
            String accessToken = getAdminCliAccessToken();
            HttpHeaders headers = getHttpHeaders(accessToken);
            HttpEntity<UserRepresentationDto> request = new HttpEntity<>(userRepresentationDto, headers);
            httpComponent.restTemplate().postForEntity(
                    keycloakUri + "/admin/realms/REALM_RASPLUS_API/users",
                    request,
                    String.class
            );
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }



    }
    private String getAdminCliAccessToken() throws JsonProcessingException {
        LoginDto login = new LoginDto();
        login.setClienteId(clientId);
        login.setClienteSecret(clientSecret);
        login.setGrantType(grandType);

        Map<String, String> clientCredentialsResponse = objectMapper.readValue(authenticationService.auth(login), Map.class);
        return clientCredentialsResponse.get("access_token");
    }
    private static HttpHeaders getHttpHeaders(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer "+ accessToken);
        return headers;
    }

}
