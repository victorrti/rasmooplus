package com.client.ws.rasmooplus.service;

import com.client.ws.rasmooplus.dto.PaymentProcessDTO;
import org.springframework.stereotype.Service;


public interface PaymentInfoService {
    Boolean process(PaymentProcessDTO dto);
}
