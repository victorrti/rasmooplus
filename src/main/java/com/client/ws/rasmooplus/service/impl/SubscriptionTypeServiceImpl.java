package com.client.ws.rasmooplus.service.impl;

import com.client.ws.rasmooplus.Model.SubscriptionType;
import com.client.ws.rasmooplus.dto.SubscriptionTypeDTO;
import com.client.ws.rasmooplus.exception.NotFoundException;
import com.client.ws.rasmooplus.repository.SubscriptionTypeRepository;
import com.client.ws.rasmooplus.service.SubscriptioTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
        Optional<SubscriptionType> subscriptionTypeOptional = subscriptionTypeRepository.findById(id);
        if(subscriptionTypeOptional.isEmpty()){
            throw  new NotFoundException("SubscriptionType não encontrado.");
        }
        return subscriptionTypeOptional.get();
    }

    @Override
    public SubscriptionType create(SubscriptionTypeDTO dto) {

        return subscriptionTypeRepository.save(
                SubscriptionType.builder()
                        .accessMonths(dto.getAccessMonths())
                        .price(dto.getPrice())
                        .name(dto.getName())
                        .productKey(dto.getProductKey())
                        .build());
    }

    @Override
    public SubscriptionType update(Long id, SubscriptionType subscriptionType) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
