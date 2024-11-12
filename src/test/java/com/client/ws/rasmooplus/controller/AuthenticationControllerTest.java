package com.client.ws.rasmooplus.controller;


import com.client.ws.rasmooplus.Model.jpa.UserCredentials;
import com.client.ws.rasmooplus.Model.redis.UserRecoveryCode;
import com.client.ws.rasmooplus.dto.UserDetailsDto;
import com.client.ws.rasmooplus.service.AuthenticationService;
import com.client.ws.rasmooplus.service.UserDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.internal.verification.Times;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
@AutoConfigureDataJpa
@AutoConfigureTestDatabase
@WebMvcTest(AuthenticationController.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles(profiles = "test")
@TestPropertySource(properties = {
        "spring.flyway.enabled=false"
})
class AuthenticationControllerTest {
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    private UserDetailsService userDetailsService;
    @MockBean
    AuthenticationService authenticationService;


    @Autowired
    private MockMvc mockMvc;
    @Test
    void given_sendRecoveryCode_when_userRecoveryCodeIsValid_then_return_NoContent() throws Exception {
        String email = "user@teste.com";
        UserRecoveryCode userRecoveryCode = new UserRecoveryCode();
        userRecoveryCode.setEmail(email);
        Mockito.doNothing().when(userDetailsService).sendRecoveryCode(email);

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/recovery-code/send").contentType(MediaType.APPLICATION_JSON_VALUE).content(objectMapper.writeValueAsString(userRecoveryCode)))
                .andExpect(
                MockMvcResultMatchers.status().isNoContent()

        );

        Mockito.verify(userDetailsService, Mockito.times(1)).sendRecoveryCode(email);

    }
    @Test
    void given_sendRecoveryCode_when_userRecoveryCodeIsMissingValue_then_return_BadRequestException() throws Exception {

        UserRecoveryCode userRecoveryCode = new UserRecoveryCode();
        Mockito.doNothing().when(userDetailsService).sendRecoveryCode(userRecoveryCode.getEmail());

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/recovery-code/send")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(userRecoveryCode)))
                //.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith("application/json"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
        Mockito.verify(userDetailsService, Mockito.times(0)).sendRecoveryCode(userRecoveryCode.getEmail());

    }

    @Test
    void given_recoveryPassword_when_userDetailsDtoIsValid_then_return_NoContent() throws Exception {
        UserDetailsDto userDetailsDto = new UserDetailsDto();
        userDetailsDto.setEmail("teste@gmail.com");
        userDetailsDto.setRecoveryCode("3454");
        Mockito.doNothing().when(userDetailsService).updatePasswordByRecoveryCode(userDetailsDto);

        mockMvc.perform(MockMvcRequestBuilders.patch("/auth/recovery-code/password").contentType(MediaType.APPLICATION_JSON_VALUE).content(objectMapper.writeValueAsString(userDetailsDto)))
                .andExpect(
                        MockMvcResultMatchers.status().isNoContent()

                );

        Mockito.verify(userDetailsService, Mockito.times(1)).updatePasswordByRecoveryCode(userDetailsDto);

    }
    @Test
    void given_recoveryPassword_when_userDetailsDtoIsMissingValid_then_return_NoContent() throws Exception {
        UserDetailsDto userDetailsDto = new UserDetailsDto();
        userDetailsDto.setEmail("test");
        userDetailsDto.setPassword("");
        userDetailsDto.setRecoveryCode("3454");
        Mockito.doNothing().when(userDetailsService).updatePasswordByRecoveryCode(userDetailsDto);

        mockMvc.perform(MockMvcRequestBuilders.patch("/auth/recovery-code/password").contentType(MediaType.APPLICATION_JSON_VALUE).content(objectMapper.writeValueAsString(userDetailsDto)))
                .andExpect(
                        MockMvcResultMatchers.status().isBadRequest()
                ).andExpect(MockMvcResultMatchers.jsonPath("$.message",is("[password=atributo invalido, email=E-mail invalido.]")));

        Mockito.verify(userDetailsService, Mockito.times(0)).updatePasswordByRecoveryCode(userDetailsDto);

    }
    @Test
    void given_recoveryCodeIsValid_when_recoverCodeAndEmailIsValid_then_return_True() throws Exception {
        String recoveryCode = "1234";
        String email = "teste@teste.com";
        Mockito.when(userDetailsService.recoveryCodeIsValid(recoveryCode,email)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.get("/auth/recovery-code").param("recoveryCode",recoveryCode).param("email",email))
                .andExpect(
                        MockMvcResultMatchers.status().isOk()

                );

        Mockito.verify(userDetailsService, Mockito.times(1)).recoveryCodeIsValid(Mockito.any(),Mockito.any());

    }

}