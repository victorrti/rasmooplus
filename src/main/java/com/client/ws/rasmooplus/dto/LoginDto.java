package com.client.ws.rasmooplus.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {
    @NotBlank(message="username e obrigatorio")
    private String username;
    @NotBlank(message="Senha e obrigatorio")
    private String password;
}
