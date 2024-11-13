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

import java.io.IOException;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/")
    public ResponseEntity<User> create(@RequestBody UserDto userDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.create(userDto));
    }

    @PatchMapping(value = "/{id}/upload-photo",consumes = MediaType.MULTIPART_FORM_DATA_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> uploadPhoto(@PathVariable Long id, @RequestPart("file") MultipartFile file) throws IOException {
        return ResponseEntity.status(HttpStatus.OK).body(userService.uploadPhoto(id,file));
    }
    @GetMapping (value = "/{id}/photo",consumes = {MediaType.IMAGE_PNG_VALUE,MediaType.IMAGE_JPEG_VALUE})
    public ResponseEntity<byte[]> downloadPhoto(@PathVariable Long id) throws IOException {
        return ResponseEntity.status(HttpStatus.OK).body(userService.downloadPhoto(id));
    }



}
