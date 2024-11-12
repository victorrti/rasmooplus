package com.client.ws.rasmooplus.Model.redis;

import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@RedisHash("recoverycode")
public class UserRecoveryCode {
    @Id
    private String id;
    @Indexed
    @Email(message = "inválido")
    @NotBlank(message = "Email invalido")
    private String email;
    private String code;

    private LocalDateTime localDateTime = LocalDateTime.now();
}
