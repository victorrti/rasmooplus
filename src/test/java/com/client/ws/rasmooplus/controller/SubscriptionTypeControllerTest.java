package com.client.ws.rasmooplus.controller;

import com.client.ws.rasmooplus.Model.jpa.SubscriptionType;
import com.client.ws.rasmooplus.dto.SubscriptionTypeDTO;
import com.client.ws.rasmooplus.service.SubscriptioTypeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
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

import static org.hamcrest.Matchers.is;

import java.math.BigDecimal;


import static org.junit.jupiter.api.Assertions.*;
@AutoConfigureDataJpa
@AutoConfigureTestDatabase
@WebMvcTest(SubscriptionTypeController.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles(profiles = "test")
@TestPropertySource(properties = {
        "spring.flyway.enabled=false"
})
class SubscriptionTypeControllerTest {
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    SubscriptioTypeService subscriptioTypeService;

    @Autowired
    private MockMvc mockMvc;


    @Test
    void given_findall_then_returnAllSubscriptionType() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/subscription-type/")).andExpect(
                MockMvcResultMatchers.status().isOk());

    }
    @Test
    void given_delete_then_returnNocontent() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete("/subscription-type/1")).andExpect(
                MockMvcResultMatchers.status().isNoContent());
        Mockito.verify(subscriptioTypeService,Mockito.times(1)).delete(1L);

    }



    @Test
    void given_findById_when_whenGetId2_then_returnOneSubscriptionType() throws Exception {
        SubscriptionType subscriptionType = new SubscriptionType();
        subscriptionType.setId(2L);
        subscriptionType.setName("ANO");
        subscriptionType.setAccessMonths(12L);
        subscriptionType.setPrice(BigDecimal.valueOf(2000.00));
        subscriptionType.setProductKey("PRODANO");
        Mockito.when(subscriptioTypeService.findById(2L)).thenReturn(subscriptionType);
        mockMvc.perform(MockMvcRequestBuilders.get("/subscription-type/2"))
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith("application/hal+json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id",is(2)));
    }

    @Test
    void given_create_when_dto9sOk_then_returnSubscriptionTypeCreated() throws Exception {
        SubscriptionType subscriptionType = new SubscriptionType();
        subscriptionType.setId(2L);
        subscriptionType.setName("ANUAL");
        subscriptionType.setAccessMonths(12L);
        subscriptionType.setPrice(BigDecimal.valueOf(2000.00));
        subscriptionType.setProductKey("PRODANO");
        SubscriptionTypeDTO subscriptionTypeDTO = new SubscriptionTypeDTO();
        subscriptionTypeDTO.setId(null);
        subscriptionTypeDTO.setName("ANUAL");
        subscriptionTypeDTO.setAccessMonths(12L);
        subscriptionTypeDTO.setPrice(BigDecimal.valueOf(2000.00));
        subscriptionTypeDTO.setProductKey("PRODANO");

        Mockito.when(subscriptioTypeService.create(subscriptionTypeDTO)).thenReturn(subscriptionType);
        mockMvc.perform(MockMvcRequestBuilders.post("/subscription-type").contentType(MediaType.APPLICATION_JSON_VALUE).content(objectMapper.writeValueAsString(subscriptionTypeDTO)))
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith("application/hal+json"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id",is(2)));
    }

    @Test
    void given_create_when_dtoIsMissingValues_then_throwBadrequestException() throws Exception {

        SubscriptionTypeDTO subscriptionTypeDTO = new SubscriptionTypeDTO();
        subscriptionTypeDTO.setId(null);
        subscriptionTypeDTO.setName("");
        subscriptionTypeDTO.setAccessMonths(13L);
        subscriptionTypeDTO.setPrice(null);
        subscriptionTypeDTO.setProductKey("22");


        mockMvc.perform(MockMvcRequestBuilders.post("/subscription-type").contentType(MediaType.APPLICATION_JSON_VALUE).content(objectMapper.writeValueAsString(subscriptionTypeDTO)))
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith("application/json"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message",is("[price=campo price não pode ser nulo, accessMonths=campo acessMonths não pode ser maior que 12, name=campo name não pode sr nulo ou vazio, productKey=campo productkey deve conter um tamanho  entre 5 e 15]")));
        Mockito.verify(subscriptioTypeService,Mockito.times(0)).create(subscriptionTypeDTO);
    }

    @Test
    void given_update_when_dtoisOk_then_returnSubscriptionTypeUpdate() throws Exception {
        SubscriptionType subscriptionType = new SubscriptionType();
        subscriptionType.setId(2L);
        subscriptionType.setName("ANUAL");
        subscriptionType.setAccessMonths(12L);
        subscriptionType.setPrice(BigDecimal.valueOf(2000.00));
        subscriptionType.setProductKey("PRODANO");
        SubscriptionTypeDTO subscriptionTypeDTO = new SubscriptionTypeDTO();
        subscriptionTypeDTO.setId(null);
        subscriptionTypeDTO.setName("ANUAL");
        subscriptionTypeDTO.setAccessMonths(12L);
        subscriptionTypeDTO.setPrice(BigDecimal.valueOf(2000.00));
        subscriptionTypeDTO.setProductKey("PRODANO");

        Mockito.when(subscriptioTypeService.update(2L,subscriptionTypeDTO)).thenReturn(subscriptionType);
        mockMvc.perform(MockMvcRequestBuilders.put("/subscription-type/2").contentType(MediaType.APPLICATION_JSON_VALUE).content(objectMapper.writeValueAsString(subscriptionTypeDTO)))
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith("application/hal+json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id",is(2)));
    }

    @Test
    void given_Update_when_dtoIsMissingValues_then_throwBadrequestException() throws Exception {

        SubscriptionTypeDTO subscriptionTypeDTO = new SubscriptionTypeDTO();
        subscriptionTypeDTO.setId(null);
        subscriptionTypeDTO.setName("");
        subscriptionTypeDTO.setAccessMonths(13L);
        subscriptionTypeDTO.setPrice(null);
        subscriptionTypeDTO.setProductKey("22");


        mockMvc.perform(MockMvcRequestBuilders.put("/subscription-type/2").contentType(MediaType.APPLICATION_JSON_VALUE).content(objectMapper.writeValueAsString(subscriptionTypeDTO)))
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith("application/json"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message",is("[price=campo price não pode ser nulo, accessMonths=campo acessMonths não pode ser maior que 12, name=campo name não pode sr nulo ou vazio, productKey=campo productkey deve conter um tamanho  entre 5 e 15]")));
        Mockito.verify(subscriptioTypeService,Mockito.times(0)).update(2L,subscriptionTypeDTO);
    }

    @Test
    void given_Update_when_idIsNullAnddtoIsValidValues_then_throwBadrequestException() throws Exception {

        SubscriptionTypeDTO subscriptionTypeDTO = new SubscriptionTypeDTO();
        subscriptionTypeDTO.setId(2L);
        subscriptionTypeDTO.setName("ANUAL");
        subscriptionTypeDTO.setAccessMonths(12L);
        subscriptionTypeDTO.setPrice(BigDecimal.valueOf(2000.00));
        subscriptionTypeDTO.setProductKey("PRODANO");


        mockMvc.perform(MockMvcRequestBuilders.put("/subscription-type/").contentType(MediaType.APPLICATION_JSON_VALUE).content(objectMapper.writeValueAsString(subscriptionTypeDTO)))
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith("application/json"))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
        Mockito.verify(subscriptioTypeService,Mockito.times(0)).update(2L,subscriptionTypeDTO);
    }



}