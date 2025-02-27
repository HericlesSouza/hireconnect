package com.hireconnect.core.repository;

import com.hireconnect.core.entity.Contract;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ContractRepository {
    Contract save(Contract contract);

    Optional<Contract> findByDepartmentIdAndFreelancerIdAndIsActiveTrue(UUID departmentId, UUID freelancerId);

    List<Contract> findAllByDepartmentIdAndIsActiveTrue(UUID departmentId);

    boolean existsByDepartmentIdAndIsActiveTrue(UUID departmentId);
}
