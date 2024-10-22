package com.client.ws.rasmooplus.service.impl;

import com.client.ws.rasmooplus.Model.SubscriptionType;
import com.client.ws.rasmooplus.dto.SubscriptionTypeDTO;
import com.client.ws.rasmooplus.exception.BadRequestException;
import com.client.ws.rasmooplus.exception.NotFoundException;
import com.client.ws.rasmooplus.repository.SubscriptionTypeRepository;
import com.client.ws.rasmooplus.service.SubscriptioTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class SubscriptionTypeServiceImpl implements SubscriptioTypeService {
    @Autowired
    private SubscriptionTypeRepository subscriptionTypeRepository;
    @Override
    public List<SubscriptionType> findAll() {
        return subscriptionTypeRepository.findAll();
    }

    @Override
    public SubscriptionType findById(Long id) {
        return this.getSusbscriptionType(id);
    }

    @Override
    public SubscriptionType create(SubscriptionTypeDTO dto) {
        if(Objects.nonNull(dto.getId())){
         throw new BadRequestException("Id deve ser nulo");
        }

        return subscriptionTypeRepository.save(
                montaSubscriptionTypeByDto(dto,null));
    }

    @Override
    public SubscriptionType update(Long id, SubscriptionTypeDTO dto) {
        getSusbscriptionType(id);
        return subscriptionTypeRepository.save(montaSubscriptionTypeByDto(dto,id));
    }

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

    public SubscriptionType montaSubscriptionTypeByDto(SubscriptionTypeDTO dto,Long id){
         return SubscriptionType.builder()
                 .id(dto.getId() != null ? dto.getId() : id)
                .accessMonths(dto.getAccessMonths())
                .price(dto.getPrice())
                .name(dto.getName())
                .productKey(dto.getProductKey())
                .build();


    }
}
