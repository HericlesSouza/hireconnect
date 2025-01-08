package com.hireconnect.infra.repository;

import com.hireconnect.core.entity.Company;
import com.hireconnect.core.repository.CompanyRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JpaCompanyRepository extends JpaRepository<Company, UUID>, CompanyRepository {
}
