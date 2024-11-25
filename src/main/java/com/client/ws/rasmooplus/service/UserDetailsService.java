package com.client.ws.rasmooplus.service;

import com.client.ws.rasmooplus.dto.UserDetailsDto;

public interface UserDetailsService {
    void sendRecoveryCode(String email);

    Boolean recoveryCodeIsValid(String recoveryCode,String email);



    void updatePasswordByRecoveryCode(UserDetailsDto userDetailsDto);
}
