package com.client.ws.rasmooplus.service;

import com.client.ws.rasmooplus.Model.SubscriptionType;
import com.client.ws.rasmooplus.dto.SubscriptionTypeDTO;

import java.util.List;

public interface SubscriptioTypeService {
    List<SubscriptionType>  findAll();
    SubscriptionType findById(Long id);
    SubscriptionType create(SubscriptionTypeDTO dto);
    SubscriptionType update(Long id,SubscriptionTypeDTO dto);
    void delete(Long id);


}
