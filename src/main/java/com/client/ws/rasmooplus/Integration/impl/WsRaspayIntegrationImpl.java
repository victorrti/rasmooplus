package com.client.ws.rasmooplus.Integration.impl;

import com.client.ws.rasmooplus.Integration.WsRaspayIntgration;
import com.client.ws.rasmooplus.dto.wsraspay.CostumerDTO;
import com.client.ws.rasmooplus.dto.wsraspay.OrderDTO;
import com.client.ws.rasmooplus.dto.wsraspay.PaymentDTO;
import org.hibernate.query.Order;

public class WsRaspayIntegrationImpl implements WsRaspayIntgration {

    @Override
    public CostumerDTO createCostumer(CostumerDTO costumerDTO) {
        return null;
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
