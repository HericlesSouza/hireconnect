package com.hireconnect.core.service;

import com.hireconnect.core.entity.*;
import com.hireconnect.core.exception.BusinessException;
import com.hireconnect.core.exception.ResourceNotFoundException;
import com.hireconnect.core.repository.FreelancerRepository;
import com.hireconnect.core.repository.JobVacanciesApplicationRepository;
import com.hireconnect.core.repository.JobVacanciesRepository;
import com.hireconnect.infra.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class FreelancerService {
    private final JobVacanciesApplicationRepository jobVacanciesApplicationRepository;
    private final JobVacanciesRepository jobVacanciesRepository;
    private final FreelancerRepository freelancerRepository;
    private final SecurityUtils securityUtils;

    @Transactional
    public JobVacanciesApplication apply(UUID jobId) {
        JobVacancies jobVacancies = this.jobVacanciesRepository.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("The job with the given ID does not exist."));

        User user = this.securityUtils.getAuthenticatedUser();

        Freelancer freelancer = this.freelancerRepository.findByUserId(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User ID not found"));

        JobVacanciesApplication jobVacanciesApplication = new JobVacanciesApplication();
        jobVacanciesApplication.setApplication(jobVacancies, freelancer);

        return this.jobVacanciesApplicationRepository.save(jobVacanciesApplication);
    }

    public List<JobVacanciesApplication> listApplications(String status) {
        User user = this.securityUtils.getAuthenticatedUser();
        UUID freelancerId = user.getFreelancer().getId();

        JobApplicationsStatus statusEnum = null;

        if (status != null && !status.isEmpty()) {
            try {
                statusEnum = JobApplicationsStatus.valueOf(status.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new BusinessException("The provided status '" + status + "' is invalid. Accepted values are: "
                        + Arrays.toString(JobApplicationsStatus.values()));
            }
        }

        return this.jobVacanciesApplicationRepository.findByFreelancerIdAndStatus(freelancerId, statusEnum);
    }
}
