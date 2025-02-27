package com.hireconnect.infra.repository;

import com.hireconnect.core.entity.Contract;
import com.hireconnect.core.repository.ContractRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JpaContractRepository extends JpaRepository<Contract, UUID>, ContractRepository {
}
