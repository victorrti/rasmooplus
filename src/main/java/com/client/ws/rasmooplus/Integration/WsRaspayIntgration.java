package com.client.ws.rasmooplus.Integration;

import com.client.ws.rasmooplus.dto.wsraspay.CostumerDTO;
import com.client.ws.rasmooplus.dto.wsraspay.OrderDTO;
import com.client.ws.rasmooplus.dto.wsraspay.PaymentDTO;

public interface WsRaspayIntgration {
    public CostumerDTO createCostumer(CostumerDTO costumerDTO);
    public OrderDTO createOrder(OrderDTO orderDTO);

    Boolean processPayment(PaymentDTO dto);
}
