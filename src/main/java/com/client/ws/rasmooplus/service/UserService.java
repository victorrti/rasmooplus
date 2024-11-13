package com.client.ws.rasmooplus.service;

import com.client.ws.rasmooplus.Model.jpa.User;
import com.client.ws.rasmooplus.dto.UserDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserService {
    public User create(UserDto userDto);

    public User uploadPhoto(Long id, MultipartFile file) throws IOException;

    public User findById(Long id);


    byte[] downloadPhoto(Long id);
}
