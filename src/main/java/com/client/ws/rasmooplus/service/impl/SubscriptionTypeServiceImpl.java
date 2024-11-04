package com.client.ws.rasmooplus.service.impl;

import com.client.ws.rasmooplus.Model.jpa.SubscriptionType;
import com.client.ws.rasmooplus.controller.SubscriptionTypeController;
import com.client.ws.rasmooplus.dto.SubscriptionTypeDTO;
import com.client.ws.rasmooplus.exception.BadRequestException;
import com.client.ws.rasmooplus.exception.NotFoundException;
import com.client.ws.rasmooplus.mapper.SubscriptionTypeMapper;
import com.client.ws.rasmooplus.repository.jpa.SubscriptionTypeRepository;
import com.client.ws.rasmooplus.service.SubscriptioTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class SubscriptionTypeServiceImpl implements SubscriptioTypeService {
    @Autowired
    private SubscriptionTypeRepository subscriptionTypeRepository;

    private static final  String UPDATE ="update";
    private static final  String DELETE ="delete";

    @Override
    @Cacheable(value="subscriptionType")
    public List<SubscriptionType> findAll() {
        return subscriptionTypeRepository.findAll();
    }
    @Cacheable(value="subscriptionType",key="#id")
    @Override
    public SubscriptionType findById(Long id) {
        return this.getSusbscriptionType(id).add(WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(SubscriptionTypeController.class).findById(id)).withSelfRel()
        ).add(WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(SubscriptionTypeController.class).update(id,new SubscriptionTypeDTO())).withRel(UPDATE)
        ).add(WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(SubscriptionTypeController.class).delete(id)).withRel(DELETE)
        );
    }
    @CacheEvict(value="subscriptionType",allEntries = true)
    @Override
    public SubscriptionType create(SubscriptionTypeDTO dto) {
        if(Objects.nonNull(dto.getId())){
         throw new BadRequestException("Id deve ser nulo");
        }

        return subscriptionTypeRepository.save(
                SubscriptionTypeMapper.fromDtoToEntity(dto));
    }
    @CacheEvict(value="subscriptionType",allEntries = true)
    @Override
    public SubscriptionType update(Long id, SubscriptionTypeDTO dto) {
        getSusbscriptionType(id);
        dto.setId(id);
        return subscriptionTypeRepository.save(SubscriptionTypeMapper.fromDtoToEntity(dto));
    }
    @CacheEvict(value="subscriptionType",allEntries = true)
    @Override
    public void delete(Long id) {
        getSusbscriptionType(id);
        subscriptionTypeRepository.deleteById(id);
    }

    public SubscriptionType getSusbscriptionType(Long id){
        Optional<SubscriptionType> subscriptionTypeOptional = subscriptionTypeRepository.findById(id);
        if(subscriptionTypeOptional.isEmpty()){
            throw  new NotFoundException("SubscriptionType não encontrado.");
        }
        return subscriptionTypeOptional.get();
    }


}
