package com.client.ws.rasmooplus.controller;

import com.client.ws.rasmooplus.Model.jpa.User;
import com.client.ws.rasmooplus.dto.UserDto;
import com.client.ws.rasmooplus.repository.redis.UserRecoveryCodeRepository;
import com.client.ws.rasmooplus.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/")
    public ResponseEntity<User> create(@RequestBody UserDto userDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.create(userDto));
    }

    @PostMapping(value = "/{id}/upload-photo",consumes = MediaType.MULTIPART_FORM_DATA_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> uploadPhoto(@PathVariable Long id, @RequestPart("file") MultipartFile file){
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }



}
