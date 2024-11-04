package com.client.ws.rasmooplus.Model.redis;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@RedisHash("recoverycode")
public class RecoveryCode {
    @Id
    private String id;
    private String email;
    private String code;

    private LocalDateTime localDateTime = LocalDateTime.now();
}
