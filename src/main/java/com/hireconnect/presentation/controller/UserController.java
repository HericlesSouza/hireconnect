package com.hireconnect.presentation.controller;

import com.hireconnect.adapters.dto.user.JobApplyResponseDTO;
import com.hireconnect.adapters.dto.user.ListApplicationsResponseDTO;
import com.hireconnect.adapters.dto.user.UpdateFreelancerDTO;
import com.hireconnect.adapters.dto.user.UserWithFreelancerDTO;
import com.hireconnect.adapters.mapper.Mapper;
import com.hireconnect.adapters.mapper.MapperJobApplications;
import com.hireconnect.core.entity.*;
import com.hireconnect.core.service.FreelancerService;
import com.hireconnect.core.service.UserService;
import com.hireconnect.core.utils.UUIDUtils;
import jakarta.validation.Valid;
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
    private final MapperJobApplications mapperJobApplications;
    private final UserService userService;
    private final Mapper mapper;

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

        ListApplicationsResponseDTO responseDTO = this.mapperJobApplications.toResponseDTO(applications);

        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping()
    public ResponseEntity<UserWithFreelancerDTO> update(
            @RequestBody
            @Valid
            UpdateFreelancerDTO payload
    ) {
        User user = this.mapper.map(payload, User.class);
        Freelancer freelancer = null;

        if (payload.getTypeUser().equalsIgnoreCase(TypeUser.FREELANCER.name())) {
            freelancer = this.mapper.map(payload, Freelancer.class);
        }

        User userUpdated = this.userService.update(user, freelancer);

        UserWithFreelancerDTO response = this.mapper.map(userUpdated, UserWithFreelancerDTO.class);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}