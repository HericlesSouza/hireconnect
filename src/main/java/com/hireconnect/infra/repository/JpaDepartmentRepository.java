package com.hireconnect.infra.repository;

import com.hireconnect.core.entity.Department;
import com.hireconnect.core.repository.DepartmentRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JpaDepartmentRepository extends JpaRepository<Department, UUID>, DepartmentRepository {
}
