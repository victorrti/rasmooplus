package com.client.ws.rasmooplus.Integration.impl;

import com.client.ws.rasmooplus.Integration.WsRaspayIntgration;
import com.client.ws.rasmooplus.dto.wsraspay.CostumerDTO;
import com.client.ws.rasmooplus.dto.wsraspay.OrderDTO;
import com.client.ws.rasmooplus.dto.wsraspay.PaymentDTO;
import org.apache.coyote.Response;
import org.hibernate.query.Order;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Base64;


@Component
public class WsRaspayIntegrationImpl implements WsRaspayIntgration {
    private RestTemplate restTemplate;
    private final HttpHeaders headers;
    @Value("${webservices.raspay.host}")
    private  String raspayHost;
    @Value("${webservices.raspay.costumer}")
    private  String raspayCostumer;
    @Value("${webservices.raspay.payment}")
    private  String raspayPayment;
    @Value("${webservices.raspay.order}")
    private  String raspayorder;

    public WsRaspayIntegrationImpl(){
        restTemplate = new RestTemplate();
        headers = getHttpHeaders();
    }

    @Override
    public CostumerDTO createCostumer(CostumerDTO costumerDTO) {
        try{
            String credentials= "rasmooplus:r@asm00";
            String base64 = Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));
            this.headers.add("Authorization","Basic "+base64);
            HttpEntity<CostumerDTO> request = new HttpEntity<>(costumerDTO,this.headers);
            ResponseEntity<CostumerDTO> response = restTemplate.exchange(raspayHost+raspayCostumer, HttpMethod.POST,request, CostumerDTO.class);
            return response.getBody();
        }catch(Exception e){
            throw  e;
        }

    }

    @Override
    public OrderDTO createOrder(OrderDTO orderDTO) {
        try{
            String credentials= "rasmooplus:r@asm00";
            String base64 = Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));
            this.headers.add("Authorization","Basic "+base64);
            HttpEntity<OrderDTO> request = new HttpEntity<>(orderDTO,this.headers);
            ResponseEntity<OrderDTO> response = restTemplate.exchange(raspayHost+raspayorder, HttpMethod.POST,request, OrderDTO.class);
            return response.getBody();
        }catch(Exception e){
            throw  e;
        }
    }

    @Override
    public Boolean processPayment(PaymentDTO dto) {
        try{
            String credentials= "rasmooplus:r@asm00";
            String base64 = Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));
            this.headers.add("Authorization","Basic "+base64);
            HttpEntity<PaymentDTO> request = new HttpEntity<>(this.headers);
            ResponseEntity<Boolean> response = restTemplate.exchange(raspayHost+raspayPayment, HttpMethod.POST,request, Boolean.class);
            return response.getBody();
        }catch(Exception e){
            throw  e;
        }
    }

    private static HttpHeaders getHttpHeaders() {
        String credentials= "rasmooplus:r@asm00";
        String base64 = Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization","Basic "+base64);
        return headers;
    }
}
