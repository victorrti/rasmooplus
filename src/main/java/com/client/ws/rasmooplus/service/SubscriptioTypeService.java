package com.client.ws.rasmooplus.service;

import com.client.ws.rasmooplus.Model.SubscriptionType;

import java.util.List;

public interface SubscriptioTypeService {
    List<SubscriptionType>  findAll();
    SubscriptionType findById();
    SubscriptionType create(SubscriptionType subscriptionType);
    SubscriptionType update(Long id,SubscriptionType subscriptionType);
    void delete(Long id);


}
