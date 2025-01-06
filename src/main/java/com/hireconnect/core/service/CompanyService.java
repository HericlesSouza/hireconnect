package com.hireconnect.core.service;

import com.hireconnect.core.entity.Company;
import com.hireconnect.core.entity.User;
import com.hireconnect.core.exception.BusinessException;
import com.hireconnect.core.exception.ResourceNotFoundException;
import com.hireconnect.core.repository.CompanyRepository;
import com.hireconnect.core.repository.UserRepository;
import com.hireconnect.core.utils.UUIDUtils;
import com.hireconnect.infra.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class CompanyService {
    private final CompanyRepository repository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final SecurityUtils securityUtils;

    @Transactional
    public Company create(Company payload) {
        User user = this.securityUtils.getAuthenticatedUser();

        this.validateCompanyNameExists(payload.getName());

        if (user.getCompany() != null) {
            throw new BusinessException("User already has a company associated.");
        }

        Company company = new Company(payload.getName(), payload.getDescription(), user);
        user.setCompany(company);
        return this.repository.save(company);
    }

    public Company getById(String id) {
        UUID uuid = UUIDUtils.fromString(id);
        return this.repository.findById(uuid).orElseThrow(() -> new ResourceNotFoundException("Company not found. The company with the given ID does not exist."));
    }

    @Transactional
    public Company update(Company payload) {
        User user = this.securityUtils.getAuthenticatedUser();

        if (user.getCompany() == null) {
            throw new ResourceNotFoundException("This user does not have any company associated with them.");
        }

        Company company = repository.findById(user.getCompany().getId())
                .orElseThrow(() -> new ResourceNotFoundException("This user does not have any company associated with them."));

        if (payload.getName() != null && !payload.getName().isEmpty()) {
            this.validateCompanyNameExists(payload.getName());
        }

        mapNonNullProperties(payload, company);

        return company;
    }

    @Transactional
    public void delete() {
        User user = this.securityUtils.getAuthenticatedUser();

        if (user.getCompany() == null) {
        throw new ResourceNotFoundException("This user does not have any company associated with them.");
        }

        user.setCompany(null);
    }

    public void grantAdminPermission() {
    }

    private void validateCompanyNameExists(String name) {
        if (this.repository.existsByName(name)) {
            throw new BusinessException("A company with this name already exists.");
        }
    }

    private void mapNonNullProperties(Company source, Company target) {
        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        modelMapper.map(source, target);
    }
}
