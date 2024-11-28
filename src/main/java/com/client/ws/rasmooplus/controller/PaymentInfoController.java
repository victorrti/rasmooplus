package com.client.ws.rasmooplus.controller;

import com.client.ws.rasmooplus.dto.PaymentProcessDTO;
import com.client.ws.rasmooplus.dto.UserPaymentInfoDto;
import com.client.ws.rasmooplus.service.PaymentInfoService;
import com.client.ws.rasmooplus.service.impl.PaymenteInfoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
public class PaymentInfoController {
    @Autowired
     PaymentInfoService paymentInfoService;

    @PostMapping("/process")
    @PreAuthorize(value="hasAnyAuthority('CLIENT_READ_WRITE','ADMIN_READ','ADMIN_WRITE')")
    public ResponseEntity<Boolean> process(@RequestBody PaymentProcessDTO dto){
        return ResponseEntity.ok().body(paymentInfoService.process(dto));
    }


}
