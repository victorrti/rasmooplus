package com.client.ws.rasmooplus.controller;

import com.client.ws.rasmooplus.Model.redis.UserRecoveryCode;
import com.client.ws.rasmooplus.dto.LoginDto;
import com.client.ws.rasmooplus.dto.TokenDto;
import com.client.ws.rasmooplus.dto.UserDetailsDto;
import com.client.ws.rasmooplus.exception.BadRequestException;
import com.client.ws.rasmooplus.service.AuthenticationService;
import com.client.ws.rasmooplus.service.TokenService;
import com.client.ws.rasmooplus.service.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    AuthenticationService authenticationService;
    @Autowired
    private UserDetailsService userDetailsService;



    @PostMapping
    public ResponseEntity<TokenDto> auth(@RequestBody LoginDto loginDto) {
        return ResponseEntity.status(HttpStatus.OK).body(authenticationService.auth(loginDto));

    }

    @PostMapping("/recovery-code/send")
    public ResponseEntity<?> sendRecoveryCode(@RequestBody UserRecoveryCode userRecoveryCode){
       userDetailsService.sendRecoveryCode(userRecoveryCode.getEmail());
       return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);

    }
    @GetMapping("/recovery-code")
    public ResponseEntity<Boolean> recoveryCodeIsValid(@RequestParam("recoveryCode") String recoveryCode,@RequestParam("email") String email){
       Boolean isValid = userDetailsService.recoveryCodeIsValid(recoveryCode,email);
       return ResponseEntity.status(HttpStatus.OK).body(isValid);

    }
    @PatchMapping("/recovery-code/password")
    public ResponseEntity<?> recoveryPassword(@RequestBody UserDetailsDto userDetailsDto){
        userDetailsService.updatePasswordByRecoveryCode(userDetailsDto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);

    }

}
