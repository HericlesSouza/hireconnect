package com.hireconnect.presentation.controller;

import com.hireconnect.core.entity.Department;
import com.hireconnect.core.service.DepartmentService;
import com.hireconnect.core.utils.UUIDUtils;
import com.hireconnect.presentation.dto.department.CreateDepartmentDTO;
import com.hireconnect.presentation.dto.department.DepartmentDTO;
import com.hireconnect.presentation.dto.department.DepartmentWithCompanyDTO;
import com.hireconnect.presentation.mapper.DepartmentMapper;
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
    private final DepartmentMapper departmentMapper;
    private final DepartmentService departmentService;

    @PreAuthorize("@securityService.canAccessCompany(authentication.principal, #companyId)")
    @PostMapping
    public ResponseEntity<DepartmentWithCompanyDTO> create(@PathVariable String companyId, @RequestBody @Valid CreateDepartmentDTO payload) {
        UUID companyUUID = UUIDUtils.fromString(companyId);
        Department departmentToEntity = this.departmentMapper.toEntity(payload);
        Department department = this.departmentService.create(departmentToEntity, companyUUID);
        DepartmentWithCompanyDTO departmentWithCompanyDTO = this.departmentMapper.toDepartmentWithCompanyDTO(department);
        return ResponseEntity.status(HttpStatus.CREATED).body(departmentWithCompanyDTO);
    }


    @PreAuthorize("@securityService.canAccessCompany(authentication.principal, #companyId)")
    @GetMapping
    public ResponseEntity<List<DepartmentDTO>> listDepartmentsFromCompany(@PathVariable String companyId) {
        UUID companyUUID = UUIDUtils.fromString(companyId);
        List<Department> departments = this.departmentService.listDepartmentsFromCompany(companyUUID);
        List<DepartmentDTO> departmentDTOS = departments.stream().map(this.departmentMapper::toDepartmentDTO).toList();
        return ResponseEntity.status(HttpStatus.OK).body(departmentDTOS);
    }
}