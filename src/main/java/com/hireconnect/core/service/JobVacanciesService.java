package com.hireconnect.core.service;

import com.hireconnect.adapters.mapper.ModelMapperUtils;
import com.hireconnect.core.entity.*;
import com.hireconnect.core.exception.BusinessException;
import com.hireconnect.core.exception.ResourceNotFoundException;
import com.hireconnect.core.repository.ContractRepository;
import com.hireconnect.core.repository.DepartmentRepository;
import com.hireconnect.core.repository.JobVacanciesApplicationRepository;
import com.hireconnect.core.repository.JobVacanciesRepository;
import com.hireconnect.core.service.dto.UpdateStatusApplicationResponse;
import com.hireconnect.core.utils.UUIDUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class JobVacanciesService {
    private final JobVacanciesRepository repository;
    private final DepartmentRepository departmentRepository;
    private final JobVacanciesApplicationRepository jobVacanciesApplicationRepository;
    private final ContractRepository contractRepository;

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
    public void listApplications(UUID id) {
        List<JobVacanciesApplication> applications = this.jobVacanciesApplicationRepository.findByJobVacancyId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Applications not found with the given Job ID"));
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

    @Transactional
    public UpdateStatusApplicationResponse updateStatusApplication(String applicationId, String status) {
        if (!"accepted".equalsIgnoreCase(status) && !"rejected".equalsIgnoreCase(status)) {
            throw new BusinessException("status: must be 'accepted' or 'rejected'");
        }

        UUID applicationUUID = UUIDUtils.fromString(applicationId);

        JobVacanciesApplication application = jobVacanciesApplicationRepository.findById(applicationUUID)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found with the provided ID"));

        application.setStatus(JobApplicationsStatus.valueOf(status.toUpperCase()));

        JobVacanciesApplication updatedApplication = jobVacanciesApplicationRepository.save(application);

        Contract contract = this.contractRepository.findByDepartmentIdAndFreelancerId(
                        application.getJobVacancy().getDepartment().getId(),
                        application.getFreelancer().getId()
                )
                .orElse(null);

        if ("accepted".equalsIgnoreCase(status) && contract == null) {
            contract = new Contract();
            contract.setFreelancer(application.getFreelancer());
            contract.setJobVacancy(application.getJobVacancy());
            contract.setDepartment(application.getJobVacancy().getDepartment());
            contract = this.contractRepository.save(contract);
        }

        return new UpdateStatusApplicationResponse(updatedApplication, contract);
    }

    @Transactional
    public void dismissEmployee(UUID departmentId, UUID freelancerId) {
        Contract contract = this.contractRepository.findByDepartmentIdAndFreelancerId(departmentId, freelancerId)
                .orElseThrow(() -> new ResourceNotFoundException("Department or freelancer not found"));

        contract.setActive(false);

        this.contractRepository.save(contract);
    }

    private void validateJobVacancyExists(String name, UUID departmentId) {
        boolean exists = this.repository.existsByTitleAndDepartmentId(name, departmentId);
        if (exists) {
            throw new BusinessException("A job vacancy with this name already exists in the department.");
        }
    }
}