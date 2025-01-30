package com.hireconnect.infra.repository;

import com.hireconnect.core.entity.Freelancer;
import com.hireconnect.core.repository.FreelancerRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaFreelancerRepository extends JpaRepository<Freelancer, UUID>, FreelancerRepository {
}
