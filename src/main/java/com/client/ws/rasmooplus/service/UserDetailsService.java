package com.client.ws.rasmooplus.service;

import com.client.ws.rasmooplus.dto.UserDetailsDto;
import com.client.ws.rasmooplus.dto.oauth.UserRepresentationDto;

public interface UserDetailsService {
    void sendRecoveryCode(String email);

    Boolean recoveryCodeIsValid(String recoveryCode,String email);



    void updatePasswordByRecoveryCode(UserDetailsDto userDetailsDto);

    void createAuthUser(UserRepresentationDto userRepresentationDto);
}
