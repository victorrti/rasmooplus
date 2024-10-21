package com.client.ws.rasmooplus.service.impl;

import com.client.ws.rasmooplus.Model.SubscriptionType;
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
            return null;
        }
        return subscriptionTypeOptional.get();
    }

    @Override
    public SubscriptionType create(SubscriptionType subscriptionType) {
        return null;
    }

    @Override
    public SubscriptionType update(Long id, SubscriptionType subscriptionType) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
