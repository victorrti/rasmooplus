package com.client.ws.rasmooplus.repository.redis;

import com.client.ws.rasmooplus.Model.redis.RecoveryCode;
import org.springframework.data.repository.CrudRepository;

public interface RecoveryCodeRepository extends CrudRepository<RecoveryCode,String> {
}
