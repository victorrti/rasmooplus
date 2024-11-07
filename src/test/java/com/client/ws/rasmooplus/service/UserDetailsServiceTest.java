package com.client.ws.rasmooplus.service;

import com.client.ws.rasmooplus.Integration.MailIntegration;
import com.client.ws.rasmooplus.Integration.impl.MailIntegrationImpl;
import com.client.ws.rasmooplus.Model.jpa.UserCredentials;
import com.client.ws.rasmooplus.Model.redis.UserRecoveryCode;
import com.client.ws.rasmooplus.exception.BadRequestException;
import com.client.ws.rasmooplus.exception.NotFoundException;
import com.client.ws.rasmooplus.repository.jpa.UserDetailsRepository;
import com.client.ws.rasmooplus.repository.redis.UserRecoveryCodeRepository;
import com.client.ws.rasmooplus.service.impl.UserDetailsServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class UserDetailsServiceTest {
    @Mock
    private UserDetailsRepository userDetailsRepository;

    @Mock
    private UserRecoveryCodeRepository userRecoveryCodeRepository;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;
    @Mock
    private MailIntegrationImpl mailIntegration;
    @Test
    void given_LoadByUsernameAndPass_when_UsernameAndPasswordIsNotNullAndPassValid_then_returnUserCredentials(){
        String username = "teste@gmail.com";
        String pass = "123456";
        UserCredentials userCredentials = new UserCredentials();
        userCredentials.setPassword(new BCryptPasswordEncoder().encode(pass));
        userCredentials.setUsername(username);
        userCredentials.setId(1L);

        when(userDetailsRepository.findByUsername(username)).thenReturn(Optional.of(userCredentials));
        Assertions.assertEquals(userCredentials,userDetailsService.loadUserByUsernameAndPass(username,pass));
        verify(userDetailsRepository,times(1)).findByUsername(Mockito.any());

    }
    @Test
    void given_LoadByUsernameAndPass_when_UserCredentialsIsNotFound_then_throwNotFoundException(){
        String username = "teste@gmail.com";
        String pass = "123456";
        UserCredentials userCredentials = new UserCredentials();
        userCredentials.setPassword(new BCryptPasswordEncoder().encode(pass));
        userCredentials.setUsername(username);
        userCredentials.setId(1L);

        when(userDetailsRepository.findByUsername(username)).thenReturn(Optional.empty());
        Assertions.assertThrows(NotFoundException.class,() -> userDetailsService.loadUserByUsernameAndPass(username,pass));
        verify(userDetailsRepository,times(1)).findByUsername(Mockito.any());

    }

    @Test
    void given_LoadByUsernameAndPass_when_UsernameAndPasswordIsNotNullAndPassIsNotValid_then_returnUserCredentials(){
        String username = "teste@gmail.com";
        String pass = "123456";
        UserCredentials userCredentials = new UserCredentials();
        userCredentials.setPassword(new BCryptPasswordEncoder().encode("45678"));
        userCredentials.setUsername(username);
        userCredentials.setId(1L);

        when(userDetailsRepository.findByUsername(username)).thenReturn(Optional.of(userCredentials));
        Assertions.assertThrows(BadRequestException.class,() -> userDetailsService.loadUserByUsernameAndPass(username,pass));
        verify(userDetailsRepository,times(1)).findByUsername(Mockito.any());

    }

    @Test
    void given_sendRecoveryCode_when_emailIsNotNullAndRecoveryCodeIsNotFoundAndUserCredentialsFound_then_EnviaEmail(){

        String email = "teste@gmail.com";
        UserRecoveryCode userRecoveryCode = new UserRecoveryCode();


        when(userRecoveryCodeRepository.findByEmail(email)).thenReturn(Optional.empty());


        UserCredentials userCredentials = new UserCredentials();
        userCredentials.setPassword(new BCryptPasswordEncoder().encode("45678"));
        userCredentials.setUsername(email);
        userCredentials.setId(1L);

        when(userDetailsRepository.findByUsername(email)).thenReturn(Optional.of(userCredentials));

        userDetailsService.sendRecoveryCode(email);


        verify(userRecoveryCodeRepository,times(1)).findByEmail(email);
        verify(userDetailsRepository,times(1)).findByUsername(email);
        verify(userRecoveryCodeRepository,times(1)).save(Mockito.any());
        verify(mailIntegration,times(1)).send(Mockito.any(),Mockito.any(),Mockito.any());


    }

    @Test
    void given_sendRecoveryCode_when_emailIsNotNullAndRecoveryCodeIsFound_then_EnviaEmail(){

        String email = "teste@gmail.com";
        UserRecoveryCode userRecoveryCode = new UserRecoveryCode();
        userRecoveryCode.setEmail(email);
        userRecoveryCode.setCode("12345");
        userRecoveryCode.setId("id");
        userRecoveryCode.setLocalDateTime(LocalDateTime.now());


        when(userRecoveryCodeRepository.findByEmail(email)).thenReturn(Optional.of(userRecoveryCode));



        userDetailsService.sendRecoveryCode(email);


        verify(userRecoveryCodeRepository,times(1)).findByEmail(email);
        verify(userDetailsRepository,times(0)).findByUsername(email);
        verify(userRecoveryCodeRepository,times(1)).save(Mockito.any());
        verify(mailIntegration,times(1)).send(Mockito.any(),Mockito.any(),Mockito.any());


    }

    @Test
    void given_sendRecoveryCode_when_emailIsNotNullAndRecoveryCodeIsNotFoundAndUserCredentialsisNotFound_then_ThrowNotFoundException(){

        String email = "teste@gmail.com";
        UserRecoveryCode userRecoveryCode = new UserRecoveryCode();


        when(userRecoveryCodeRepository.findByEmail(email)).thenReturn(Optional.empty());


        UserCredentials userCredentials = new UserCredentials();
        userCredentials.setPassword(new BCryptPasswordEncoder().encode("45678"));
        userCredentials.setUsername(email);
        userCredentials.setId(1L);

        when(userDetailsRepository.findByUsername(email)).thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class,() -> userDetailsService.sendRecoveryCode(email));


        verify(userRecoveryCodeRepository,times(1)).findByEmail(email);
        verify(userDetailsRepository,times(1)).findByUsername(email);
        verify(userRecoveryCodeRepository,times(0)).save(Mockito.any());
        verify(mailIntegration,times(0)).send(Mockito.any(),Mockito.any(),Mockito.any());


    }




}
