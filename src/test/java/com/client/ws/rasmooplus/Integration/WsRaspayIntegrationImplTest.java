package com.client.ws.rasmooplus.Integration;

import com.client.ws.rasmooplus.dto.wsraspay.CostumerDTO;
import com.client.ws.rasmooplus.dto.wsraspay.OrderDTO;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
 class WsRaspayIntegrationImplTest {
    @Autowired
    private WsRaspayIntgration wsRaspayIntgration;
     @Test
    void createCustumerWhenDtoOk(){
         CostumerDTO dto = new CostumerDTO(null,"22994287048","teste@teste.com","victor","pereira");
         wsRaspayIntgration.createCostumer(dto);

    }
    @Test
    void creteOrderWhenDtoOk(){
        OrderDTO dto = new OrderDTO(null,"22994287048",10L,"ProductMensal");
        wsRaspayIntgration.createOrder(dto);

    }
}
