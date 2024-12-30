package com.hireconnect.core.useCase;

import com.hireconnect.core.entity.Company;
import com.hireconnect.core.entity.User;
import com.hireconnect.core.exception.BusinessException;
import com.hireconnect.core.repository.CompanyRepository;
import com.hireconnect.core.repository.UserRepository;
import com.hireconnect.infra.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CompanyUseCase {
    private final CompanyRepository repository;
    private final UserRepository userRepository;

    @Transactional
    public Company create(Company payload) {
        User user = SecurityUtils.getAuthenticatedUser();

        if (user.getCompany() != null) {
            throw new BusinessException("User already has a company associated");
        }

        Company company = new Company(payload.getName(), payload.getDescription(), user);
        user.setCompany(company);
        return this.repository.save(company);
    }
}
