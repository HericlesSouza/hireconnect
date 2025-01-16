package com.hireconnect.core.repository;

import com.hireconnect.core.entity.JobVacancies;

import java.util.Optional;
import java.util.UUID;

public interface JobVacanciesRepository {
    Optional<JobVacancies> findById(UUID uuid);

    Optional<JobVacancies> findByIdAndDepartmentId(UUID uuid, UUID departmentId);

    boolean existsByTitleAndDepartmentId(String title, UUID uuid);

    JobVacancies save(JobVacancies jobVacancies);
}