package com.client.ws.rasmooplus.Integration;

import com.client.ws.rasmooplus.dto.wsraspay.CostumerDTO;
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
}
