package com.client.ws.rasmooplus.repository.redis;

import com.client.ws.rasmooplus.Model.jpa.User;
import com.client.ws.rasmooplus.Model.redis.UserRecoveryCode;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRecoveryCodeRepository extends CrudRepository<UserRecoveryCode,String> {
    Optional<UserRecoveryCode> findByEmail(String email);
}
