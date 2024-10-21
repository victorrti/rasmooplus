package com.client.ws.rasmooplus.controller;

import com.client.ws.rasmooplus.Model.SubscriptionType;
import com.client.ws.rasmooplus.repository.SubscriptionTypeRepository;
import com.client.ws.rasmooplus.service.SubscriptioTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/subscription-type")
public class SubscriptionTypeController {
    @Autowired
    private SubscriptioTypeService subscriptioTypeService;
    @GetMapping("/")
    public ResponseEntity<List<SubscriptionType>> findAll(){
        return ResponseEntity.status(HttpStatus.OK).body(subscriptioTypeService.findAll());
    }
}
