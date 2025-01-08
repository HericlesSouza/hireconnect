package com.hireconnect.core.repository;

import com.hireconnect.core.entity.Department;

import java.util.UUID;

public interface DepartmentRepository {
    boolean existsByNameAndCompanyId(String name, UUID companyId);

    Department save(Department department);
}
