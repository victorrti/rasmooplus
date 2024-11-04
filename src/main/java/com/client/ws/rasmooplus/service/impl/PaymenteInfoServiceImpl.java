package com.client.ws.rasmooplus.service.impl;

import com.client.ws.rasmooplus.Integration.MailIntegration;
import com.client.ws.rasmooplus.Integration.WsRaspayIntgration;
import com.client.ws.rasmooplus.Model.jpa.User;
import com.client.ws.rasmooplus.Model.jpa.UserCredentials;
import com.client.ws.rasmooplus.Model.jpa.UserPaymentInfo;
import com.client.ws.rasmooplus.dto.PaymentProcessDTO;
import com.client.ws.rasmooplus.dto.wsraspay.CostumerDTO;
import com.client.ws.rasmooplus.dto.wsraspay.CreditCardDTO;
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
import com.client.ws.rasmooplus.repository.jpa.UserDetailsRepository;
import com.client.ws.rasmooplus.repository.jpa.UserPaymentInfoRepository;
import com.client.ws.rasmooplus.repository.jpa.UserRepository;
import com.client.ws.rasmooplus.repository.jpa.UserTypeRepository;
import com.client.ws.rasmooplus.service.PaymentInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
  UserDetailsRepository userDetailsRepository;
    @Autowired
     UserTypeRepository userTypeRepository;



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
        if(userOpt.isEmpty()){
            throw  new NotFoundException("Usuario não encontrado");
        }
        User user = userOpt.get();
        if(Objects.nonNull(user.getSubscriptionType())){
            throw  new BusinessException("Pagamento não podera ser processado pois o usuario já possui assinatura");
        }
        CostumerDTO costumerDTO =  wsRaspayIntgration.createCostumer(CostumerMapper.build(user));


        OrderDTO orderDTO = wsRaspayIntgration.createOrder(OrderMapper.build(costumerDTO.getId(),dto));
        CreditCardDTO creditCardDTO = CreditCardMapper.build(dto.getUserPaymentInfoDto(),user.getCpf());
        PaymentDTO paymentDTO = PaymentMapper.build(costumerDTO.getId(),orderDTO.getId(),creditCardDTO);
        Boolean processado  = wsRaspayIntgration.processPayment(paymentDTO);
        if(processado){
            UserPaymentInfo userPaymentInfo = UserPaymentInfoMapper.fromDtoToEntity(dto.getUserPaymentInfoDto(),user);
            userPaymentInfoRepository.save(userPaymentInfo);
            var userType = userTypeRepository.findById(UserTypeEnum.ALUNO.getId());
            UserCredentials userCredentials = new UserCredentials(null,user.getNome(),new BCryptPasswordEncoder().encode(defoultPassword),userType.get());
            userDetailsRepository.save(userCredentials);
            mailIntegration.send(user.getEmail(),"Login: "+user.getEmail()+" senha: rasmooAluno","Acesso liberado");
        }

        return processado;

    }
}
