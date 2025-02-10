package com.hireconnect.core.repository;

import com.hireconnect.core.entity.JobApplicationsStatus;
import com.hireconnect.core.entity.JobVacanciesApplication;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JobVacanciesApplicationRepository {
    JobVacanciesApplication save(JobVacanciesApplication jobVacanciesApplication);

    Optional<List<JobVacanciesApplication>> findByJobVacancyId(UUID uuid);

    List<JobVacanciesApplication> findByFreelancerIdAndStatus(UUID uuid, JobApplicationsStatus status);
}
