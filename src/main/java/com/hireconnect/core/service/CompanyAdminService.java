package com.hireconnect.core.service;

import com.hireconnect.core.entity.Company;
import com.hireconnect.core.entity.CompanyAdmin;
import com.hireconnect.core.entity.User;
import com.hireconnect.core.exception.ApiException;
import com.hireconnect.core.exception.BusinessException;
import com.hireconnect.core.exception.ResourceNotFoundException;
import com.hireconnect.core.repository.CompanyAdminRepository;
import com.hireconnect.core.repository.CompanyRepository;
import com.hireconnect.core.repository.UserRepository;
import com.hireconnect.infra.security.SecurityUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CompanyAdminService {
    private final CompanyAdminRepository companyAdminRepository;
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final SecurityUtils securityUtils;

    @Transactional
    public void grantAdminPermission(UUID companyId, UUID userId) {
        User userAuthenticated = this.securityUtils.getAuthenticatedUser();
        Company companyFromUserAuthenticated = userAuthenticated.getCompany();

        Company company = this.companyRepository.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Company with ID " + companyId + " not found."));

        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with ID " + userId + " not found."));

        if (companyFromUserAuthenticated == null || !companyFromUserAuthenticated.getId().equals(companyId)) {
            throw new ApiException("Access denied: You do not have permission to access this resource.", HttpStatus.FORBIDDEN);
        }

        if (this.companyAdminRepository.findByCompanyIdAndUserId(companyId, userId).isPresent()) {
            throw new BusinessException("User is already an admin for this company.");
        }

        CompanyAdmin companyAdmin = new CompanyAdmin(user, company);

        this.companyAdminRepository.save(companyAdmin);
    }
}
