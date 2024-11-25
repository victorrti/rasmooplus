package com.client.ws.rasmooplus.service;

import com.client.ws.rasmooplus.Integration.impl.MailIntegrationImpl;
import com.client.ws.rasmooplus.Model.redis.UserRecoveryCode;
import com.client.ws.rasmooplus.exception.BadRequestException;
import com.client.ws.rasmooplus.exception.NotFoundException;
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
import org.springframework.test.util.ReflectionTestUtils;


import java.time.LocalDateTime;
import java.util.Optional;

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


    private  static final String USERNAME = "username@teste.com.br";
    private  static final String PASSWORD = "12345";

    private static final String RECOVERY_CODE = "2354";



    @Test
    void given_LoadByUsernameAndPass_when_UsernameAndPasswordIsNotNullAndPassValid_then_returnUserCredentials(){

        String pass = "123456";
        UserCredentials userCredentials = new UserCredentials();
        userCredentials.setPassword(new BCryptPasswordEncoder().encode(PASSWORD));
        userCredentials.setUsername(USERNAME);
        userCredentials.setId(1L);

        when(userDetailsRepository.findByUsername(USERNAME)).thenReturn(Optional.of(userCredentials));
        Assertions.assertEquals(userCredentials,userDetailsService.loadUserByUsernameAndPass(USERNAME,PASSWORD));
        verify(userDetailsRepository,times(1)).findByUsername(Mockito.any());

    }
    @Test
    void given_LoadByUsernameAndPass_when_UserCredentialsIsNotFound_then_throwNotFoundException(){

        String pass = "123456";
        UserCredentials userCredentials = new UserCredentials();
        userCredentials.setPassword(new BCryptPasswordEncoder().encode(PASSWORD));
        userCredentials.setUsername(USERNAME);
        userCredentials.setId(1L);

        when(userDetailsRepository.findByUsername(USERNAME)).thenReturn(Optional.empty());
        Assertions.assertThrows(NotFoundException.class,() -> userDetailsService.loadUserByUsernameAndPass(USERNAME,PASSWORD));
        verify(userDetailsRepository,times(1)).findByUsername(Mockito.any());

    }

    @Test
    void given_LoadByUsernameAndPass_when_UsernameAndPasswordIsNotNullAndPassIsNotValid_then_returnUserCredentials(){

        String pass = "123456";
        UserCredentials userCredentials = new UserCredentials();
        userCredentials.setPassword(new BCryptPasswordEncoder().encode("45678"));
        userCredentials.setUsername(USERNAME);
        userCredentials.setId(1L);

        when(userDetailsRepository.findByUsername(USERNAME)).thenReturn(Optional.of(userCredentials));
        Assertions.assertThrows(BadRequestException.class,() -> userDetailsService.loadUserByUsernameAndPass(USERNAME,PASSWORD));
        verify(userDetailsRepository,times(1)).findByUsername(Mockito.any());

    }

    @Test
    void given_sendRecoveryCode_when_emailIsNotNullAndRecoveryCodeIsNotFoundAndUserCredentialsFound_then_EnviaEmail(){


        UserRecoveryCode userRecoveryCode = new UserRecoveryCode();


        when(userRecoveryCodeRepository.findByEmail(USERNAME)).thenReturn(Optional.empty());


        UserCredentials userCredentials = new UserCredentials();
        userCredentials.setPassword(new BCryptPasswordEncoder().encode("45678"));
        userCredentials.setUsername(USERNAME);
        userCredentials.setId(1L);

        when(userDetailsRepository.findByUsername(USERNAME)).thenReturn(Optional.of(userCredentials));

        userDetailsService.sendRecoveryCode(USERNAME);


        verify(userRecoveryCodeRepository,times(1)).findByEmail(USERNAME);
        verify(userDetailsRepository,times(1)).findByUsername(USERNAME);
        verify(userRecoveryCodeRepository,times(1)).save(Mockito.any());
        verify(mailIntegration,times(1)).send(Mockito.any(),Mockito.any(),Mockito.any());


    }

    @Test
    void given_sendRecoveryCode_when_emailIsNotNullAndRecoveryCodeIsFound_then_EnviaEmail(){





        when(userRecoveryCodeRepository.findByEmail(USERNAME)).thenReturn(Optional.of(loadUserRecoveryCode()));



        userDetailsService.sendRecoveryCode(USERNAME);


        verify(userRecoveryCodeRepository,times(1)).findByEmail(USERNAME);
        verify(userDetailsRepository,times(0)).findByUsername(USERNAME);
        verify(userRecoveryCodeRepository,times(1)).save(Mockito.any());
        verify(mailIntegration,times(1)).send(Mockito.any(),Mockito.any(),Mockito.any());


    }

    @Test
    void given_sendRecoveryCode_when_emailIsNotNullAndRecoveryCodeIsNotFoundAndUserCredentialsisNotFound_then_ThrowNotFoundException(){

        String email = "teste@gmail.com";
        UserRecoveryCode userRecoveryCode = new UserRecoveryCode();


        when(userRecoveryCodeRepository.findByEmail(USERNAME)).thenReturn(Optional.empty());


        UserCredentials userCredentials = new UserCredentials();
        userCredentials.setPassword(new BCryptPasswordEncoder().encode("45678"));
        userCredentials.setUsername(USERNAME);
        userCredentials.setId(1L);

        when(userDetailsRepository.findByUsername(USERNAME)).thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class,() -> userDetailsService.sendRecoveryCode(USERNAME));


        verify(userRecoveryCodeRepository,times(1)).findByEmail(USERNAME);
        verify(userDetailsRepository,times(1)).findByUsername(USERNAME);
        verify(userRecoveryCodeRepository,times(0)).save(Mockito.any());
        verify(mailIntegration,times(0)).send(Mockito.any(),Mockito.any(),Mockito.any());


    }

    @Test
    void given_recoveryCodeIsValid_when_userIsFound_then_returnTrue(){
        ReflectionTestUtils.setField(userDetailsService,"recoverTimeOut","5");
        when(userRecoveryCodeRepository.findByEmail(USERNAME)).thenReturn(Optional.of(loadUserRecoveryCode()));
        Assertions.assertTrue(userDetailsService.recoveryCodeIsValid(loadUserRecoveryCode().getCode(),loadUserRecoveryCode().getEmail()));
    }
    @Test
    void given_recoveryCodeIsNotValid_when_userIsFound_then_returnFalse(){
        ReflectionTestUtils.setField(userDetailsService,"recoverTimeOut","1");
        UserRecoveryCode userRecoveryCode = loadUserRecoveryCode();
        userRecoveryCode.setLocalDateTime(LocalDateTime.now().minusMinutes(2L));
        when(userRecoveryCodeRepository.findByEmail(USERNAME)).thenReturn(Optional.of(userRecoveryCode));
        Assertions.assertFalse(userDetailsService.recoveryCodeIsValid(loadUserRecoveryCode().getCode(),loadUserRecoveryCode().getEmail()));
    }


    private UserRecoveryCode loadUserRecoveryCode(){
        UserRecoveryCode userRecoveryCode = new UserRecoveryCode();
        userRecoveryCode.setEmail(USERNAME);
        userRecoveryCode.setCode(RECOVERY_CODE);
        userRecoveryCode.setId("id");
        userRecoveryCode.setLocalDateTime(LocalDateTime.now());
        return userRecoveryCode;

    }




}
