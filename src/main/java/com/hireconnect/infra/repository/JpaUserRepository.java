package com.hireconnect.infra.repository;

import com.hireconnect.core.entity.User;
import com.hireconnect.core.repository.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JpaUserRepository extends JpaRepository<User, UUID>, UserRepository {
}
