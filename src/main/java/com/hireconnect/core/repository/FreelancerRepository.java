package com.hireconnect.core.repository;

import com.hireconnect.core.entity.Freelancer;

import java.util.Optional;
import java.util.UUID;

public interface FreelancerRepository {
    Freelancer save(Freelancer freelancer);

    Optional<Freelancer> findByUserId(UUID uuid);
}
