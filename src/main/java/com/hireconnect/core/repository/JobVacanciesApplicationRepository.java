package com.hireconnect.core.repository;

import com.hireconnect.core.entity.JobVacanciesApplication;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JobVacanciesApplicationRepository {
    Optional<List<JobVacanciesApplication>> findByJobVacancyId(UUID uuid);
}
