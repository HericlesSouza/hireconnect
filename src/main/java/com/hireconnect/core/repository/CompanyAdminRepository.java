package com.hireconnect.core.repository;


import com.hireconnect.core.entity.CompanyAdmin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CompanyAdminRepository extends JpaRepository<CompanyAdmin, UUID> {
    Optional<CompanyAdmin> findByCompanyIdAndUserId(UUID companyId, UUID userId);
}
