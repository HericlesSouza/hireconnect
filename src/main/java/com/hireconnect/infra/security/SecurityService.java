package com.hireconnect.infra.security;

import com.hireconnect.core.entity.Department;
import com.hireconnect.core.exception.ResourceNotFoundException;
import com.hireconnect.core.repository.CompanyAdminRepository;
import com.hireconnect.core.repository.CompanyRepository;
import com.hireconnect.core.repository.DepartmentRepository;
import com.hireconnect.core.utils.UUIDUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class SecurityService {
    private final CompanyRepository companyRepository;
    private final CompanyAdminRepository companyAdminRepository;
    private final DepartmentRepository departmentRepository;

    public boolean isOwner(UUID userId, UUID companyId) {
        return this.companyRepository.existsByIdAndOwnerId(companyId, userId);
    }

    public boolean isAdmin(UUID userId, UUID companyId) {
        return this.companyAdminRepository.existsByCompanyIdAndUserId(companyId, userId);
    }

    public boolean isAdminOrOwner(UUID userId, String departmentId) {
        UUID departmentUUID = UUIDUtils.fromString(departmentId);
        Department department = this.departmentRepository.findById(departmentUUID)
                .orElseThrow(() -> new ResourceNotFoundException("The department with the given ID does not exist."));

        UUID companyId = department.getCompany().getId();

        return isAdmin(userId, companyId) || isOwner(userId, companyId);
    }

    public boolean canAccessCompany(UUID userId, String companyId) {
        UUID companyUUID = UUIDUtils.fromString(companyId);

        this.companyRepository.findById(companyUUID)
                .orElseThrow(() -> new ResourceNotFoundException("The company with the given ID does not exist."));

        return isAdmin(userId, companyUUID) || isOwner(userId, companyUUID);
    }
}
