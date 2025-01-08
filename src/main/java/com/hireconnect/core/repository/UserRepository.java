package com.hireconnect.core.repository;

import com.hireconnect.core.entity.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    Optional<User> findByEmail(String email);

    Optional<User> findById(UUID uuid);

    boolean existsByEmail(String email);

    User save(User user);
}
