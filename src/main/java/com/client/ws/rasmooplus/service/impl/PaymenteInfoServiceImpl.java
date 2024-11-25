package com.client.ws.rasmooplus.service.impl;

import com.client.ws.rasmooplus.Integration.MailIntegration;
import com.client.ws.rasmooplus.Integration.WsRaspayIntgration;
import com.client.ws.rasmooplus.Model.jpa.User;
import com.client.ws.rasmooplus.Model.jpa.UserPaymentInfo;
import com.client.ws.rasmooplus.dto.PaymentProcessDTO;
import com.client.ws.rasmooplus.dto.wsraspay.CostumerDTO;
import com.client.ws.rasmooplus.dto.wsraspay.OrderDTO;
import com.client.ws.rasmooplus.dto.wsraspay.PaymentDTO;
import com.client.ws.rasmooplus.enums.UserTypeEnum;
import com.client.ws.rasmooplus.exception.BusinessException;
import com.client.ws.rasmooplus.exception.NotFoundException;
import com.client.ws.rasmooplus.mapper.UserPaymentInfoMapper;
import com.client.ws.rasmooplus.mapper.wsraspay.CostumerMapper;
import com.client.ws.rasmooplus.mapper.wsraspay.CreditCardMapper;
import com.client.ws.rasmooplus.mapper.wsraspay.OrderMapper;
import com.client.ws.rasmooplus.mapper.wsraspay.PaymentMapper;
import com.client.ws.rasmooplus.repository.jpa.*;
import com.client.ws.rasmooplus.service.PaymentInfoService;
import com.client.ws.rasmooplus.utils.PasswordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;


import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class PaymenteInfoServiceImpl implements PaymentInfoService {
    @Value("${webservice.rasmoo.defoult.password}")
    private String defoultPassword;
    @Autowired
   UserRepository userRepository;
    @Autowired
    UserPaymentInfoRepository userPaymentInfoRepository;
    @Autowired
    WsRaspayIntgration wsRaspayIntgration;
    @Autowired
    MailIntegration mailIntegration;

    @Autowired
     UserTypeRepository userTypeRepository;
    @Autowired
    SubscriptionTypeRepository subscriptionTypeRepository;



    /*public  PaymenteInfoServiceImpl(UserRepository userRepository, UserPaymentInfoRepository userPaymentInfoRepository,
                                    WsRaspayIntgration wsRaspayIntgration, MailIntegration mailIntegration,UserDetailsRepository userDetailsRepository,UserTypeRepository userTypeRepository){
        this.userRepository = userRepository;
        this.userPaymentInfoRepository = userPaymentInfoRepository;
        this.wsRaspayIntgration = wsRaspayIntgration;
        this.mailIntegration = mailIntegration;
        this.userDetailsRepository = userDetailsRepository;
        this.userTypeRepository = userTypeRepository;
    }*/
    @Override
    public Boolean process(PaymentProcessDTO dto) {

        var userOpt = userRepository.findById(dto.getUserPaymentInfoDto().getUserId());
        if (userOpt.isEmpty()) {
            throw new NotFoundException("Usuário não encontrado");
        }
        User user = userOpt.get();
        if (Objects.nonNull(user.getSubscriptionType())) {
            throw new BusinessException("Pagamento não pode ser processado pois usuário já possui assinatura");
        }

        Boolean successPayment = getSucessPayment(dto, user);

        return createUserCredentials(dto, user, successPayment);

    }

    private boolean createUserCredentials(PaymentProcessDTO dto, User user, Boolean successPayment) {
        if (Boolean.TRUE.equals(successPayment)) {
            UserPaymentInfo userPaymentInfo = UserPaymentInfoMapper.fromDtoToEntity(dto.getUserPaymentInfoDto(), user);
            userPaymentInfoRepository.save(userPaymentInfo);

            var userTypeOpt = userTypeRepository.findById(UserTypeEnum.ALUNO.getId());

            if (userTypeOpt.isEmpty()) {
                throw new NotFoundException("UserType não encontrado");
            }
            //UserCredentials userCredentials = new UserCredentials(null, user.getEmail(), PasswordUtils.encode(defoultPassword), userTypeOpt.get());
            //userDetailsRepository.save(userCredentials);

            var subscriptionTypeOpt = subscriptionTypeRepository.findByProductKey(dto.getProductKey());

            if (subscriptionTypeOpt.isEmpty()) {
                throw new NotFoundException("SubscriptionType não encontrado");
            }
            user.setSubscriptionType(subscriptionTypeOpt.get());
            userRepository.save(user);

            mailIntegration.send(user.getEmail(), "Usuario: " + user.getEmail() + " - Senha: " + defoultPassword, "Acesso liberado");
            return true;
        }
        return false;
    }
    private Boolean getSucessPayment(PaymentProcessDTO dto, User user) {
        CostumerDTO customerDto = wsRaspayIntgration.createCostumer(CostumerMapper.build(user));
        OrderDTO orderDto = wsRaspayIntgration.createOrder(OrderMapper.build(customerDto.getId(), dto));
        PaymentDTO paymentDto = PaymentMapper.build(customerDto.getId(), orderDto.getId(), CreditCardMapper.build(dto.getUserPaymentInfoDto(), user.getCpf()));
        return wsRaspayIntgration.processPayment(paymentDto);
    }
}
