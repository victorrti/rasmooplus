package com.client.ws.rasmooplus.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {

    private String username;

    private String password;
    @NotBlank(message="clienteId não pode ser nulo ou vazio")
    private String clienteId;
    @NotBlank(message="clienteSecret não pode ser nulo ou vazio")
    private String clienteSecret;
    @NotBlank(message="grantType não pode ser nulo ou vazio")
    private String grantType;
    private String refreshToken;
}
