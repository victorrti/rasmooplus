package com.client.ws.rasmooplus.controller;

import com.client.ws.rasmooplus.Model.jpa.UserType;
import com.client.ws.rasmooplus.service.UserTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user-type")
public class UserTypeController {
    @Autowired
    UserTypeService userTypeService;

    @GetMapping("/")
    public ResponseEntity<List<UserType> > findAll(){
        return ResponseEntity.ok().body(userTypeService.findAll());
    }
}
