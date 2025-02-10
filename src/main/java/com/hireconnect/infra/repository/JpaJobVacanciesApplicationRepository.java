package com.hireconnect.infra.repository;

import com.hireconnect.core.entity.JobApplicationsStatus;
import com.hireconnect.core.entity.JobVacanciesApplication;
import com.hireconnect.core.repository.JobVacanciesApplicationRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface JpaJobVacanciesApplicationRepository extends JpaRepository<JobVacanciesApplication, UUID>, JobVacanciesApplicationRepository {
    @Query("SELECT a FROM JobVacanciesApplication a " +
            "WHERE a.freelancer.id = :freelancerId " +
            "AND (:status IS NULL OR a.status = :status)")
    List<JobVacanciesApplication> findByFreelancerIdAndOptionalStatus(
            UUID freelancerId,
            JobApplicationsStatus status);
}