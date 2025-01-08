package com.hireconnect.core.repository;


import com.hireconnect.core.entity.CompanyAdmin;

import java.util.Optional;
import java.util.UUID;

public interface CompanyAdminRepository {
    Optional<CompanyAdmin> findByCompanyIdAndUserId(UUID companyId, UUID userId);

    boolean existsByCompanyIdAndUserId(UUID companyId, UUID userId);

    CompanyAdmin save(CompanyAdmin companyAdmin);
}
