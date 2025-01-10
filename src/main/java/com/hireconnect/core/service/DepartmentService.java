package com.hireconnect.core.service;

import com.hireconnect.adapters.mapper.ModelMapperUtils;
import com.hireconnect.core.entity.Company;
import com.hireconnect.core.entity.Department;
import com.hireconnect.core.exception.BusinessException;
import com.hireconnect.core.exception.ResourceNotFoundException;
import com.hireconnect.core.repository.CompanyRepository;
import com.hireconnect.core.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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


    public List<Department> listDepartmentsFromCompany(UUID companyId) {
        Company company = this.companyRepository.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("The company with the given ID does not exist."));

        return company.getDepartments();
    }

    public Department getById(UUID departmentId, UUID companyId) {
        return this.repository.findByIdAndCompanyId(departmentId, companyId)
                .orElseThrow(() -> new ResourceNotFoundException("The department with the given ID does not exist in the specified company."));
    }

    @Transactional
    public Department update(UUID departmentId, UUID companyId, Department payload) {
        Department department = this.repository.findByIdAndCompanyId(departmentId, companyId)
                .orElseThrow(() -> new ResourceNotFoundException("The department with the given ID does not exist in the specified company."));

        if (payload.getName() != null && !payload.getName().isEmpty()) {
            this.validateDepartmentNameExists(payload.getName(), companyId);
        }

        ModelMapperUtils.mapNonNullProperties(payload, department);

        return department;
    }

    private void validateDepartmentNameExists(String name, UUID companyId) {
        boolean exists = repository.existsByNameAndCompanyId(name, companyId);
        if (exists) {
            throw new BusinessException("A department with this name already exists in the company.");
        }
    }
}
