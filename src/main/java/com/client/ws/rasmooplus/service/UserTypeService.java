package com.client.ws.rasmooplus.service;

import com.client.ws.rasmooplus.Model.jpa.UserType;

import java.util.List;

public interface UserTypeService {
    public List<UserType> findAll();
    public UserType findById(Long id);

    public void delete(Long id);


}
