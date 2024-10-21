package com.client.ws.rasmooplus.repository;

import com.client.ws.rasmooplus.Model.UserPaymentInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPaymentInfoRepository extends JpaRepository<UserPaymentInfo,Long> {
}
