package com.hireconnect.infra.repository;

import com.hireconnect.core.entity.CompanyAdmin;
import com.hireconnect.core.repository.CompanyAdminRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JpaCompanyAdminRepository extends JpaRepository<CompanyAdmin, UUID>, CompanyAdminRepository {
}
