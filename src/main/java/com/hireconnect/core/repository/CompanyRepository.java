package com.hireconnect.core.repository;


import com.hireconnect.core.entity.Company;

import java.util.Optional;
import java.util.UUID;

public interface CompanyRepository {
    boolean existsByName(String name);

    boolean existsByIdAndOwnerId(UUID id, UUID ownerId);

    Optional<Company> findById(UUID uuid);

    Company save(Company company);
}
