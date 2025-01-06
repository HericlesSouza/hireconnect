package com.hireconnect.core.useCase;

import com.hireconnect.core.entity.Company;
import com.hireconnect.core.entity.CompanyAdmin;
import com.hireconnect.core.entity.User;
import com.hireconnect.core.exception.BusinessException;
import com.hireconnect.core.exception.ResourceNotFoundException;
import com.hireconnect.core.repository.CompanyAdminRepository;
import com.hireconnect.core.repository.CompanyRepository;
import com.hireconnect.core.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CompanyAdminService {
    private final CompanyAdminRepository companyAdminRepository;
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;

    @Transactional
    public void grantAdminPermission(UUID companyId, UUID userId) {
        Company company = this.companyRepository.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Company with ID " + companyId + " not found."));

        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with ID " + userId + " not found."));

        if (this.companyAdminRepository.findByCompanyIdAndUserId(companyId, userId).isPresent()) {
            throw new BusinessException("User is already an admin for this company.");
        }

        CompanyAdmin companyAdmin = new CompanyAdmin(user, company);

        this.companyAdminRepository.save(companyAdmin);
    }
}
