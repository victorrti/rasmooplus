package com.client.ws.rasmooplus.service.impl;

import com.client.ws.rasmooplus.Integration.MailIntegration;
import com.client.ws.rasmooplus.Integration.impl.MailIntegrationImpl;
import com.client.ws.rasmooplus.Model.jpa.User;
import com.client.ws.rasmooplus.Model.jpa.UserCredentials;
import com.client.ws.rasmooplus.Model.redis.UserRecoveryCode;
import com.client.ws.rasmooplus.dto.UserDetailsDto;
import com.client.ws.rasmooplus.exception.BadRequestException;
import com.client.ws.rasmooplus.exception.NotFoundException;
import com.client.ws.rasmooplus.repository.jpa.UserDetailsRepository;
import com.client.ws.rasmooplus.repository.redis.UserRecoveryCodeRepository;
import com.client.ws.rasmooplus.service.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserDetailsRepository userDetailsRepository;
    @Autowired
    UserRecoveryCodeRepository userRecoveryCodeRepository;
    @Autowired
    private MailIntegration mailIntegration;
    @Value("${webservice.rasplus.redis.recovercody.timeout}")
    private String recoverTimeOut;
    @Override
    public UserCredentials loadUserByUsernameAndPass(String username, String pass) {
        var userCredentialsOpt = userDetailsRepository.findByUsername(username);
        if(userCredentialsOpt.isEmpty()){
            throw  new NotFoundException("Usuario ou senha invalido");
        }
        UserCredentials userCredentials = userCredentialsOpt.get();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if(encoder.matches(pass,userCredentials.getPassword())){
            return userCredentials;
        }
        throw  new BadRequestException("Usuario ou senha invalido");
    }



    @Override
    public void sendRecoveryCode(String email) {
        UserRecoveryCode userRecoveryCode;
        var userRecoveryCodeOpt = userRecoveryCodeRepository.findByEmail(email);
        String code = String.format("%04d", new Random().nextInt(10000));
        if(userRecoveryCodeOpt.isEmpty()){
            var userDetailsOpt = userDetailsRepository.findByUsername(email);
            if(userDetailsOpt.isEmpty()){
                throw new NotFoundException("Usuario não encontrado.");
            }
            userRecoveryCode = new UserRecoveryCode();
            userRecoveryCode.setEmail(email);

        }else{
            userRecoveryCode = userRecoveryCodeOpt.get();
        }
        userRecoveryCode.setCode(code);
        userRecoveryCode.setLocalDateTime(LocalDateTime.now());

        userRecoveryCodeRepository.save(userRecoveryCode);
        mailIntegration.send(email,"Codigo de recuperação de conta "+code,"Codigo de recuperação de conta " +code);

    }

    @Override
    public Boolean recoveryCodeIsValid(String recoveryCode, String email) {
        var userRecoveryCodeOpt = userRecoveryCodeRepository.findByEmail(email);
        if(userRecoveryCodeOpt.isEmpty()){
            throw new NotFoundException("Usuario não encontrado.");
        }
        UserRecoveryCode userRecoveryCode = userRecoveryCodeOpt.get();
        LocalDateTime timeout = userRecoveryCode.getLocalDateTime().plusMinutes(Long.parseLong(recoverTimeOut));
        LocalDateTime now = LocalDateTime.now();
        if(recoveryCode.equals(userRecoveryCode.getCode()) && now.isBefore(timeout)){
            return true;
        }
        return false;
    }
    @Override
    public void updatePasswordByRecoveryCode(UserDetailsDto userDetailsDto) {
        if(recoveryCodeIsValid(userDetailsDto.getRecoveryCode(),userDetailsDto.getEmail())){
            var userDetailsOpt = userDetailsRepository.findByUsername(userDetailsDto.getEmail());
            UserCredentials userCredentials = userDetailsOpt.get();
            userCredentials.setPassword(new BCryptPasswordEncoder().encode(userDetailsDto.getPassword()) );
            userDetailsRepository.save(userCredentials);

        }

    }

}
