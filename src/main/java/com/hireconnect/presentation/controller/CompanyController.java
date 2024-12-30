package com.hireconnect.presentation.controller;

import com.hireconnect.core.entity.Company;
import com.hireconnect.core.useCase.CompanyUseCase;
import com.hireconnect.presentation.dto.company.CompanyAndUserDTO;
import com.hireconnect.presentation.dto.company.CreateCompanyDTO;
import com.hireconnect.presentation.mapper.CompanyMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/company")
public class CompanyController {
    private final CompanyUseCase companyUseCase;
    private final CompanyMapper companyMapper;

    @PreAuthorize("hasRole('COMPANY')")
    @PostMapping()
    public ResponseEntity<CompanyAndUserDTO> create(@RequestBody @Valid CreateCompanyDTO payload) {
        Company companyEntity = this.companyMapper.toCompanyEntity(payload);
        Company company = this.companyUseCase.create(companyEntity);
        CompanyAndUserDTO companyAndUserDTO = this.companyMapper.toCompanyAndUserDTO(company);
        return ResponseEntity.status(HttpStatus.CREATED).body(companyAndUserDTO);
    }
}
