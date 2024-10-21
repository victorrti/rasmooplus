package com.client.ws.rasmooplus.repository;

import com.client.ws.rasmooplus.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
