package com.hireconnect.presentation.controller;

import com.hireconnect.adapters.dto.user.JobApplyResponseDTO;
import com.hireconnect.adapters.dto.user.ListApplicationsResponseDTO;
import com.hireconnect.adapters.mapper.MapperJobApplications;
import com.hireconnect.core.entity.JobVacancies;
import com.hireconnect.core.entity.JobVacanciesApplication;
import com.hireconnect.core.entity.User;
import com.hireconnect.core.service.FreelancerService;
import com.hireconnect.core.utils.UUIDUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {
    private final FreelancerService freelancerService;
    private final MapperJobApplications mapper;

    @PreAuthorize("hasRole('FREELANCER')")
    @PostMapping("/apply/{jobId}")
    public ResponseEntity<JobApplyResponseDTO> applyJob(@PathVariable String jobId) {
        UUID jobUUID = UUIDUtils.fromString(jobId);
        JobVacanciesApplication apply = this.freelancerService.apply(jobUUID);

        User userFreelancer = apply.getFreelancer().getUser();
        JobVacancies jobVacancies = apply.getJobVacancy();

        JobApplyResponseDTO response = new JobApplyResponseDTO(
                new JobApplyResponseDTO.JobDTO(
                        jobVacancies.getId(),
                        jobVacancies.getTitle(),
                        jobVacancies.getDescription()
                ),
                new JobApplyResponseDTO.FreelancerDTO(
                        userFreelancer.getId(),
                        userFreelancer.getName(),
                        userFreelancer.getEmail()
                ),
                apply.getCreatedAt()
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PreAuthorize("hasRole('FREELANCER')")
    @GetMapping("/applications")
    public ResponseEntity<ListApplicationsResponseDTO> listApplications(@RequestParam(required = false) String status) {
        List<JobVacanciesApplication> applications = this.freelancerService.listApplications(status);

        ListApplicationsResponseDTO responseDTO = this.mapper.toResponseDTO(applications);

        return ResponseEntity.ok(responseDTO);
    }
}
