package com.hireconnect.infra.repository;

import com.hireconnect.core.entity.JobVacanciesApplication;
import com.hireconnect.core.repository.JobVacanciesApplicationRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaJobVacanciesApplicationRepository extends JpaRepository<JobVacanciesApplication, UUID>, JobVacanciesApplicationRepository {
}
