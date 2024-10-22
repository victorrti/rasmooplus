package com.client.ws.rasmooplus.service;

import com.client.ws.rasmooplus.Model.User;
import com.client.ws.rasmooplus.dto.UserDto;

public interface UserService {
    public User create(UserDto userDto);
}
