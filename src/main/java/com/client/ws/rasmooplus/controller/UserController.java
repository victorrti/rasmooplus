package com.client.ws.rasmooplus.controller;

import com.client.ws.rasmooplus.Model.jpa.User;
import com.client.ws.rasmooplus.dto.UserDto;
import com.client.ws.rasmooplus.dto.oauth.UserRepresentationDto;
import com.client.ws.rasmooplus.repository.redis.UserRecoveryCodeRepository;
import com.client.ws.rasmooplus.service.UserDetailsService;
import com.client.ws.rasmooplus.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    UserDetailsService userDetailsService;


    @PostMapping
    @PreAuthorize(value = "hasAnyAuthority('CLIENT_READ_WRITE','ADMIN_READ','ADMIN_WRITE')")
    public ResponseEntity<User> create(@Valid @RequestBody UserDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.create(dto));
    }

    @PostMapping("/credentials")
    @PreAuthorize(value = "hasAnyAuthority('ADMIN_READ','ADMIN_WRITE')")
    public ResponseEntity<User> createAuthUser(@Valid @RequestBody UserRepresentationDto dto) {
        userDetailsService.createAuthUser(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping(value = "/{id}/upload-photo",consumes = MediaType.MULTIPART_FORM_DATA_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(value="hasAnyAuthority('USER_READ','USER_WRITE','ADMIN_READ','ADMIN_WRITE')")
    public ResponseEntity<User> uploadPhoto(@PathVariable Long id, @RequestPart("file") MultipartFile file) throws IOException {
        return ResponseEntity.status(HttpStatus.OK).body(userService.uploadPhoto(id,file));
    }
    @GetMapping (value = "/{id}/photo",consumes = {MediaType.IMAGE_PNG_VALUE,MediaType.IMAGE_JPEG_VALUE})
    @PreAuthorize(value="hasAnyAuthority('USER_READ','USER_WRITE','ADMIN_READ','ADMIN_WRITE')")
    public ResponseEntity<byte[]> downloadPhoto(@PathVariable Long id) throws IOException {
        return ResponseEntity.status(HttpStatus.OK).body(userService.downloadPhoto(id));
    }



}
