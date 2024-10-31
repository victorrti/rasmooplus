package com.client.ws.rasmooplus.controller;

import com.client.ws.rasmooplus.dto.PaymentProcessDTO;
import com.client.ws.rasmooplus.dto.UserPaymentInfoDto;
import com.client.ws.rasmooplus.service.PaymentInfoService;
import com.client.ws.rasmooplus.service.impl.PaymenteInfoServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
public class PaymentInfoController {
    private PaymentInfoService paymentInfoService;

    @PostMapping("/process")
    public ResponseEntity<Boolean> process(@RequestBody PaymentProcessDTO dto){
        return ResponseEntity.ok().body(paymentInfoService.process(dto));
    }
}