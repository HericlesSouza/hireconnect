package com.hireconnect.infra.repository;

import com.hireconnect.core.entity.JobVacancies;
import com.hireconnect.core.repository.JobVacanciesRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JpaJobVacanciesRepository extends JpaRepository<JobVacancies, UUID>, JobVacanciesRepository {
}
