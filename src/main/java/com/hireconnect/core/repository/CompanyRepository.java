package com.hireconnect.core.repository;

import com.hireconnect.core.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CompanyRepository extends JpaRepository<Company, UUID> {
    boolean existsByName(String name);

    boolean existsByIdAndOwnerId(UUID id, UUID ownerId);
}
