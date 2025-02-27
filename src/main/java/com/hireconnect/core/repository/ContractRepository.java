package com.hireconnect.core.repository;

import com.hireconnect.core.entity.Contract;

import java.util.Optional;
import java.util.UUID;

public interface ContractRepository {
    Contract save(Contract contract);

    Optional<Contract> findByDepartmentIdAndFreelancerId(UUID departmentId, UUID freelancerId);
}
