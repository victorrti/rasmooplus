package com.client.ws.rasmooplus.service.impl;

import com.client.ws.rasmooplus.Integration.WsRaspayIntgration;
import com.client.ws.rasmooplus.Model.User;
import com.client.ws.rasmooplus.Model.UserPaymentInfo;
import com.client.ws.rasmooplus.dto.PaymentProcessDTO;
import com.client.ws.rasmooplus.dto.UserPaymentInfoDto;
import com.client.ws.rasmooplus.dto.wsraspay.CostumerDTO;
import com.client.ws.rasmooplus.exception.BusinessException;
import com.client.ws.rasmooplus.exception.NotFoundException;
import com.client.ws.rasmooplus.mapper.UserPaymentInfoMapper;
import com.client.ws.rasmooplus.mapper.wsraspay.CostumerMapper;
import com.client.ws.rasmooplus.repository.UserPaymentInfoRepository;
import com.client.ws.rasmooplus.repository.UserRepository;
import com.client.ws.rasmooplus.service.PaymentInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class PaymenteInfoServiceImpl implements PaymentInfoService {
    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private UserPaymentInfoRepository userPaymentInfoRepository;
    @Autowired
    private WsRaspayIntgration wsRaspayIntgration;

    public  PaymenteInfoServiceImpl(){}
    @Override
    public Boolean process(PaymentProcessDTO dto) {

        var userOpt = userRepository.findById(dto.getUserPaymentInfoDto().getUserId());
        if(userOpt.isEmpty()){
            throw  new NotFoundException("Usuario não encontrado");
        }
        User user = userOpt.get();
        if(Objects.nonNull(user.getSubscriptionType())){
            throw  new BusinessException("Pagamento não podera ser processado pois o usuario já possui assinatura");
        }
        CostumerDTO costumerDTO =  wsRaspayIntgration.createCostumer(CostumerMapper.build(user));
        UserPaymentInfo userPaymentInfo = UserPaymentInfoMapper.fromDtoToEntity(dto.getUserPaymentInfoDto(),user);
        userPaymentInfoRepository.save(userPaymentInfo);
    }
}
