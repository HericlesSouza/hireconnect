package com.hireconnect.presentation.controller;

import com.hireconnect.adapters.dto.jobVacancies.*;
import com.hireconnect.adapters.mapper.Mapper;
import com.hireconnect.core.entity.JobVacancies;
import com.hireconnect.core.service.JobVacanciesService;
import com.hireconnect.core.service.dto.UpdateStatusApplicationResponse;
import com.hireconnect.core.utils.UUIDUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/department/{departmentId}/job")
public class JobVacanciesController {
    private final Mapper mapper;
    private final JobVacanciesService jobVacanciesService;

    @PreAuthorize("@securityService.isAdminOrOwner(authentication.principal, #departmentId)")
    @PostMapping
    public ResponseEntity<JobVacanciesWithDepartmentDTO> create(
            @PathVariable String departmentId,
            @RequestBody @Valid CreateJobVacanciesDTO payload
    ) {
        UUID departmentUUID = UUIDUtils.fromString(departmentId);
        JobVacancies jobVacancies = this.mapper.map(payload, JobVacancies.class);
        JobVacancies jobVacanciesCreated = this.jobVacanciesService.create(jobVacancies, departmentUUID);
        JobVacanciesWithDepartmentDTO response = this.mapper.map(jobVacanciesCreated, JobVacanciesWithDepartmentDTO.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PreAuthorize("@securityService.isAdminOrOwner(authentication.principal, #departmentId)")
    @PutMapping("/{jobId}")
    public ResponseEntity<JobVacanciesWithDepartmentDTO> update(
            @PathVariable String departmentId,
            @PathVariable String jobId,
            @RequestBody @Valid UpdateJobVacanciesDTO payload
    ) {
        UUID departmentUUID = UUIDUtils.fromString(departmentId);
        UUID jobUUID = UUIDUtils.fromString(jobId);
        JobVacancies jobVacancies = this.mapper.map(payload, JobVacancies.class);
        JobVacancies jobVacanciesUpdated = this.jobVacanciesService.update(jobVacancies, departmentUUID, jobUUID);
        JobVacanciesWithDepartmentDTO response = this.mapper.map(jobVacanciesUpdated, JobVacanciesWithDepartmentDTO.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PreAuthorize("@securityService.isAdminOrOwner(authentication.principal, #departmentId)")
    @PatchMapping("/{jobId}/activate")
    public ResponseEntity<JobVacanciesWithDepartmentDTO> activate(
            @PathVariable String jobId,
            @PathVariable String departmentId
    ) {
        UUID jobUUID = UUIDUtils.fromString(jobId);
        UUID departmentUUID = UUIDUtils.fromString(departmentId);
        JobVacancies jobVacanciesDeleted = this.jobVacanciesService.activate(jobUUID, departmentUUID);
        JobVacanciesWithDepartmentDTO jobVacanciesWithDepartmentDTO = this.mapper.map(jobVacanciesDeleted, JobVacanciesWithDepartmentDTO.class);
        return ResponseEntity.status(HttpStatus.OK).body(jobVacanciesWithDepartmentDTO);
    }

    @PreAuthorize("@securityService.isAdminOrOwner(authentication.principal, #departmentId)")
    @DeleteMapping("/{jobId}")
    public ResponseEntity<JobVacanciesWithDepartmentDTO> softDelete(
            @PathVariable String jobId,
            @PathVariable String departmentId
    ) {
        UUID jobUUID = UUIDUtils.fromString(jobId);
        UUID departmentUUID = UUIDUtils.fromString(departmentId);
        JobVacancies jobVacanciesDeleted = this.jobVacanciesService.softDelete(jobUUID, departmentUUID);
        JobVacanciesWithDepartmentDTO jobVacanciesWithDepartmentDTO = this.mapper.map(jobVacanciesDeleted, JobVacanciesWithDepartmentDTO.class);
        return ResponseEntity.status(HttpStatus.OK).body(jobVacanciesWithDepartmentDTO);
    }

    @PreAuthorize("@securityService.isAdminOrOwner(authentication.principal, #departmentId)")
    @PatchMapping("/{applicationId}/status")
    public ResponseEntity<UpdateStatusApplicationDTO> updateStatusApplication(
            @RequestBody @Valid CreateApplicationStatusDTO status,
            @PathVariable String applicationId,
            @PathVariable String departmentId
    ) {
        UpdateStatusApplicationResponse updateStatusApplication = this.jobVacanciesService.updateStatusApplication(applicationId, status.getStatus());
        UpdateStatusApplicationDTO updateStatusApplicationDTO = this.mapper.map(updateStatusApplication, UpdateStatusApplicationDTO.class);
        return ResponseEntity.status(HttpStatus.OK).body(updateStatusApplicationDTO);
    }
}
