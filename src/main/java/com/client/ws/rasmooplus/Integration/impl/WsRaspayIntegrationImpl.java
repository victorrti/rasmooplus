package com.client.ws.rasmooplus.Integration.impl;

import com.client.ws.rasmooplus.Integration.WsRaspayIntgration;
import com.client.ws.rasmooplus.dto.wsraspay.CostumerDTO;
import com.client.ws.rasmooplus.dto.wsraspay.OrderDTO;
import com.client.ws.rasmooplus.dto.wsraspay.PaymentDTO;
import org.apache.coyote.Response;
import org.hibernate.query.Order;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WsRaspayIntegrationImpl implements WsRaspayIntgration {
    private RestTemplate restTemplate;

    private static final String URL = "http://localhost:80881/ws-raspay/";

    public WsRaspayIntegrationImpl(){
        restTemplate = new RestTemplate();
    }

    @Override
    public CostumerDTO createCostumer(CostumerDTO costumerDTO) {
        try{
            HttpEntity<CostumerDTO> request = new HttpEntity<>(costumerDTO);
            ResponseEntity<CostumerDTO> response = restTemplate.exchange(URL+"v1/custumer", HttpMethod.POST,request, CostumerDTO.class);
            return response.getBody();
        }catch(Exception e){
            throw  e;
        }

    }

    @Override
    public OrderDTO createOrder(OrderDTO orderDTO) {
        return null;
    }

    @Override
    public Boolean processPayment(PaymentDTO dto) {
        return null;
    }
}
