package com.hireconnect.core.service;

import com.hireconnect.core.entity.Company;
import com.hireconnect.core.entity.Department;
import com.hireconnect.core.exception.BusinessException;
import com.hireconnect.core.exception.ResourceNotFoundException;
import com.hireconnect.core.repository.CompanyRepository;
import com.hireconnect.core.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class DepartmentService {
    private final DepartmentRepository repository;
    private final CompanyRepository companyRepository;

    @Transactional
    public Department create(Department payload, UUID companyId) {
        this.validateDepartmentNameExists(payload.getName(), companyId);

        Company company = this.companyRepository.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("The company with the given ID does not exist."));

       company.addDepartment(payload);

        return this.repository.save(payload);
    }

    private void validateDepartmentNameExists(String name, UUID companyId) {
        boolean exists = repository.existsByNameAndCompanyId(name, companyId);
        if (exists) {
            throw new BusinessException("A department with this name already exists in the company.");
        }
    }
}
