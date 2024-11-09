package com.client.ws.rasmooplus.service.impl;

import com.client.ws.rasmooplus.Model.jpa.UserType;
import com.client.ws.rasmooplus.exception.BadRequestException;
import com.client.ws.rasmooplus.exception.NotFoundException;
import com.client.ws.rasmooplus.repository.jpa.UserTypeRepository;
import com.client.ws.rasmooplus.service.UserTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserTypeServiceImpl implements UserTypeService {
    @Autowired
    UserTypeRepository userTypeRepository;
    @Override
    public List<UserType> findAll() {
        return userTypeRepository.findAll();
    }

    @Override
    public UserType findById(Long id) {
        return getUserType(id);
    }

    @Override
    public void delete(Long id) {
        userTypeRepository.deleteById(id);
    }



    public UserType getUserType(Long id){
        Optional<UserType> userTypeOptional = userTypeRepository.findById(id);
        if(id == null){
            throw  new BadRequestException("Identificador não informado");
        }
        if(userTypeOptional.isEmpty()){
            throw  new NotFoundException("UserType não encontrado");
        }

        return userTypeOptional.get();
    }




}
