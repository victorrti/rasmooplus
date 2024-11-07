package com.client.ws.rasmooplus.service.impl;

import com.client.ws.rasmooplus.Model.jpa.User;
import com.client.ws.rasmooplus.Model.jpa.UserType;
import com.client.ws.rasmooplus.Model.redis.UserRecoveryCode;
import com.client.ws.rasmooplus.dto.UserDto;
import com.client.ws.rasmooplus.exception.BadRequestException;
import com.client.ws.rasmooplus.exception.NotFoundException;
import com.client.ws.rasmooplus.mapper.UserMapper;
import com.client.ws.rasmooplus.repository.jpa.SubscriptionTypeRepository;
import com.client.ws.rasmooplus.repository.jpa.UserRepository;
import com.client.ws.rasmooplus.repository.jpa.UserTypeRepository;
import com.client.ws.rasmooplus.repository.redis.UserRecoveryCodeRepository;
import com.client.ws.rasmooplus.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SubscriptionTypeRepository subscriptionTypeRepository;
    @Autowired
    private UserTypeRepository userTypeRepository;

    @Override
    public User create(UserDto userDto) {
        if(Objects.nonNull(userDto.getId())){
            throw  new BadRequestException("identificador não pode vir preenchido");
        }

        Optional<UserType> userTypeOptional  = userTypeRepository.findById(userDto.getUserTypeId());

        if(userTypeOptional.isEmpty()){
            throw new NotFoundException("UserTypeId não informado ou não encontrado");
        }

        UserType userType = userTypeOptional.get();
        return userRepository.save(UserMapper.fromDtoToEntity(userDto,userType,null));

    }

    public User findByid(long id){
        return getUser(id);
    }

    public User getUser(Long id){
        if(Objects.isNull(id)){
            throw  new BadRequestException("identificador não informado");
        }

        Optional<User> userOptional = userRepository.findById(id);
        userOptional.orElseThrow(()->{
            throw new NotFoundException("Usuario não encontrado");
        });
        return userOptional.get();
    }


}
