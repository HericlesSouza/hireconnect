package com.hireconnect.core.service;

import com.hireconnect.adapters.mapper.ModelMapperUtils;
import com.hireconnect.core.entity.Department;
import com.hireconnect.core.entity.JobVacancies;
import com.hireconnect.core.exception.BusinessException;
import com.hireconnect.core.exception.ResourceNotFoundException;
import com.hireconnect.core.repository.DepartmentRepository;
import com.hireconnect.core.repository.JobVacanciesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class JobVacanciesService {
    private final JobVacanciesRepository repository;
    private final DepartmentRepository departmentRepository;

    @Transactional
    public JobVacancies create(JobVacancies payload, UUID departmentId) {
        Department department = this.departmentRepository.findById(departmentId)
                .orElseThrow(() -> new ResourceNotFoundException("The department with the given ID does not exist."));

        this.validateJobVacancyExists(payload.getTitle(), department.getId());

        JobVacancies jobVacancy = new JobVacancies(payload.getTitle(), payload.getDescription());

        department.addJobVacancies(jobVacancy);

        return this.repository.save(jobVacancy);
    }

    @Transactional
    public JobVacancies update(JobVacancies payload, UUID departmentId, UUID jobId) {
        JobVacancies jobVacancy = this.repository.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("The job with the given ID does not exist."));

        if (!jobVacancy.getTitle().equals(payload.getTitle())) {
            this.validateJobVacancyExists(payload.getTitle(), departmentId);
        }


        ModelMapperUtils.mapNonNullProperties(payload, jobVacancy);

        return jobVacancy;
    }

    @Transactional
    public JobVacancies activate(UUID jobId, UUID departmentId) {
        JobVacancies jobVacancy = this.repository.findByIdAndDepartmentId(jobId, departmentId)
                .orElseThrow(() -> new ResourceNotFoundException("The job with the given ID does not exist in the specified department."));

        jobVacancy.setActive(true);

        return jobVacancy;
    }

    @Transactional
    public JobVacancies softDelete(UUID jobId, UUID departmentId) {
        JobVacancies jobVacancy = this.repository.findByIdAndDepartmentId(jobId, departmentId)
                .orElseThrow(() -> new ResourceNotFoundException("The job with the given ID does not exist in the specified department."));

        jobVacancy.setActive(false);

        return jobVacancy;
    }

    private void validateJobVacancyExists(String name, UUID departmentId) {
        boolean exists = this.repository.existsByTitleAndDepartmentId(name, departmentId);
        if (exists) {
            throw new BusinessException("A job vacancy with this name already exists in the department.");
        }
    }
}