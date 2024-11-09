package com.client.ws.rasmooplus.Integration;

import com.client.ws.rasmooplus.Integration.impl.WsRaspayIntegrationImpl;
import com.client.ws.rasmooplus.dto.wsraspay.CostumerDTO;
import com.client.ws.rasmooplus.exception.HttpClientException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.eq;


@ExtendWith(MockitoExtension.class)
class WsRaspayIntegrationImplTest {
    @Mock
    private RestTemplate restTemplate;
    private static HttpHeaders headers;

    @InjectMocks
    private WsRaspayIntegrationImpl wsRaspayIntegration;

    @BeforeAll
    public static void loadHeaders(){
        headers = getHttpHeaders();

    }
    @Test
    void given_createCostumer_when_apiResponseIs201Created_then_returnCostumerDto(){
        ReflectionTestUtils.setField(wsRaspayIntegration,"raspayHost","http://localhost:8080");
        ReflectionTestUtils.setField(wsRaspayIntegration,"raspayCostumer","/costumer");
        CostumerDTO costumerDTO = new CostumerDTO();
        costumerDTO.setCpf("12345678987");
        HttpEntity<CostumerDTO> request = new HttpEntity<>(costumerDTO,headers);
        Mockito.when(restTemplate.exchange( eq("http://localhost:8080/costumer"),
                eq(HttpMethod.POST),
                ArgumentMatchers.any(HttpEntity.class),
                eq(CostumerDTO.class)))
                .thenReturn(ResponseEntity.of(Optional.of(costumerDTO)));
        wsRaspayIntegration.createCostumer(costumerDTO);
        Mockito.verify(restTemplate,Mockito.times(1)).exchange(eq("http://localhost:8080/costumer"),
                eq(HttpMethod.POST),
                ArgumentMatchers.any(HttpEntity.class),
                eq(CostumerDTO.class));

    }
    @Test
    void given_createCostumer_when_apiResponseIs400BadRequest_then_returnNull(){
        ReflectionTestUtils.setField(wsRaspayIntegration,"raspayHost","http://localhost:8080");
        ReflectionTestUtils.setField(wsRaspayIntegration,"raspayCostumer","/costumer");
        CostumerDTO costumerDTO = new CostumerDTO();
        costumerDTO.setCpf("12345678987");
        HttpEntity<CostumerDTO> request = new HttpEntity<>(costumerDTO,headers);
        Mockito.when(restTemplate.exchange( eq("http://localhost:8080/costumer"),
                        eq(HttpMethod.POST),
                        ArgumentMatchers.any(HttpEntity.class),
                        eq(CostumerDTO.class)))
                .thenReturn(ResponseEntity.badRequest().build());
        Assertions.assertNull(wsRaspayIntegration.createCostumer(costumerDTO));
        Mockito.verify(restTemplate,Mockito.times(1)).exchange(eq("http://localhost:8080/costumer"),
                eq(HttpMethod.POST),
                ArgumentMatchers.any(HttpEntity.class),
                eq(CostumerDTO.class));

    }
    @Test
    void given_createCostumer_when_apiResponseGetThorws_then_thorwHttpClientException(){
        ReflectionTestUtils.setField(wsRaspayIntegration,"raspayHost","http://localhost:8080");
        ReflectionTestUtils.setField(wsRaspayIntegration,"raspayCostumer","/costumer");
        CostumerDTO costumerDTO = new CostumerDTO();
        costumerDTO.setCpf("12345678987");
        HttpEntity<CostumerDTO> request = new HttpEntity<>(costumerDTO,headers);
        Mockito.when(restTemplate.exchange( eq("http://localhost:8080/costumer"),
                        eq(HttpMethod.POST),
                        ArgumentMatchers.any(HttpEntity.class),
                        eq(CostumerDTO.class)))
                .thenThrow(HttpClientException.class);
        Assertions.assertThrows(HttpClientException.class,() -> wsRaspayIntegration.createCostumer(costumerDTO));
        Mockito.verify(restTemplate,Mockito.times(1)).exchange(eq("http://localhost:8080/costumer"),
                eq(HttpMethod.POST),
                ArgumentMatchers.any(HttpEntity.class),
                eq(CostumerDTO.class));
    }
    private static HttpHeaders getHttpHeaders() {
        String credentials= "rasmooplus:r@asm00";
        String base64 = Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization","Basic "+base64);
        return headers;
    }


}