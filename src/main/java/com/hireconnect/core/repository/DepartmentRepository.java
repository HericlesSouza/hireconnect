package com.hireconnect.core.repository;

import com.hireconnect.core.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DepartmentRepository extends JpaRepository<Department, UUID> {
    boolean existsByNameAndCompanyId(String name, UUID companyId);
}
