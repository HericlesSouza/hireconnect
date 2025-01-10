package com.hireconnect.presentation.controller;

import com.hireconnect.adapters.dto.department.CreateDepartmentDTO;
import com.hireconnect.adapters.dto.department.DepartmentDTO;
import com.hireconnect.adapters.dto.department.DepartmentWithCompanyDTO;
import com.hireconnect.adapters.dto.department.UpdateDepartmentDTO;
import com.hireconnect.adapters.mapper.Mapper;
import com.hireconnect.core.entity.Department;
import com.hireconnect.core.service.DepartmentService;
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
@RequestMapping("/company/{companyId}/department")
public class DepartmentController {
    private final Mapper mapper;
    private final DepartmentService departmentService;

    @PreAuthorize("@securityService.canAccessCompany(authentication.principal, #companyId)")
    @PostMapping
    public ResponseEntity<DepartmentWithCompanyDTO> create(@PathVariable String companyId, @RequestBody @Valid CreateDepartmentDTO payload) {
        UUID companyUUID = UUIDUtils.fromString(companyId);
        Department departmentToEntity = this.mapper.map(payload, Department.class);
        Department department = this.departmentService.create(departmentToEntity, companyUUID);
        DepartmentWithCompanyDTO departmentWithCompanyDTO = this.mapper.map(department, DepartmentWithCompanyDTO.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(departmentWithCompanyDTO);
    }


    @PreAuthorize("@securityService.canAccessCompany(authentication.principal, #companyId)")
    @GetMapping
    public ResponseEntity<List<DepartmentDTO>> listDepartmentsFromCompany(@PathVariable String companyId) {
        UUID companyUUID = UUIDUtils.fromString(companyId);
        List<Department> departments = this.departmentService.listDepartmentsFromCompany(companyUUID);
        List<DepartmentDTO> departmentDTOS = departments.stream().map(department -> this.mapper.map(department, DepartmentDTO.class)).toList();
        return ResponseEntity.status(HttpStatus.OK).body(departmentDTOS);
    }

    @PreAuthorize("@securityService.canAccessCompany(authentication.principal, #companyId)")
    @GetMapping("/{departmentId}")
    public ResponseEntity<DepartmentWithCompanyDTO> getById(@PathVariable String companyId, @PathVariable String departmentId) {
        UUID companyUUID = UUIDUtils.fromString(companyId);
        UUID departmentUUID = UUIDUtils.fromString(departmentId);
        Department department = this.departmentService.getById(departmentUUID, companyUUID);
        DepartmentWithCompanyDTO departmentWithCompanyDTO = this.mapper.map(department, DepartmentWithCompanyDTO.class);
        return ResponseEntity.status(HttpStatus.OK).body(departmentWithCompanyDTO);
    }

    @PreAuthorize("@securityService.canAccessCompany(authentication.principal, #companyId)")
    @PatchMapping("/{departmentId}")
    public ResponseEntity<DepartmentWithCompanyDTO> update(@PathVariable String companyId, @PathVariable String departmentId, @Valid UpdateDepartmentDTO payload) {
        UUID companyUUID = UUIDUtils.fromString(companyId);
        UUID departmentUUID = UUIDUtils.fromString(departmentId);
        Department departmentPayload = this.mapper.map(payload, Department.class);
        Department department = this.departmentService.update(departmentUUID, companyUUID, departmentPayload);
        DepartmentWithCompanyDTO departmentWithCompanyDTO = this.mapper.map(department, DepartmentWithCompanyDTO.class);
        return ResponseEntity.status(HttpStatus.OK).body(departmentWithCompanyDTO);
    }
}