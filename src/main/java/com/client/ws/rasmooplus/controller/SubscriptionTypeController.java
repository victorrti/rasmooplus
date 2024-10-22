package com.client.ws.rasmooplus.controller;

import com.client.ws.rasmooplus.Model.SubscriptionType;
import com.client.ws.rasmooplus.dto.SubscriptionTypeDTO;
import com.client.ws.rasmooplus.exception.NotFoundException;
import com.client.ws.rasmooplus.repository.SubscriptionTypeRepository;
import com.client.ws.rasmooplus.service.SubscriptioTypeService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/subscription-type")
public class SubscriptionTypeController {
    @Autowired
    private SubscriptioTypeService subscriptioTypeService;
    @GetMapping("/")
    public ResponseEntity<List<SubscriptionType>> findAll(){
        return ResponseEntity.status(HttpStatus.OK).body(subscriptioTypeService.findAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<SubscriptionType> findById(@PathVariable("id") Long id){
        return ResponseEntity.status(HttpStatus.OK).body(subscriptioTypeService.findById(id));
    }
    @PostMapping()
    public ResponseEntity<SubscriptionType> create(@RequestBody SubscriptionTypeDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(subscriptioTypeService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubscriptionType> update(@PathVariable Long id,@RequestBody SubscriptionTypeDTO dto){
        return ResponseEntity.status(HttpStatus.OK).body(subscriptioTypeService.update(id,dto));
    }



}
