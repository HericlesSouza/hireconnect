package com.hireconnect.presentation.controller;

import com.hireconnect.core.entity.Company;
import com.hireconnect.core.service.CompanyService;
import com.hireconnect.presentation.dto.company.CompanyAndUserDTO;
import com.hireconnect.presentation.dto.company.CreateCompanyDTO;
import com.hireconnect.presentation.dto.company.UpdateCompanyDTO;
import com.hireconnect.presentation.mapper.CompanyMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("/company")
public class CompanyController {
    private final CompanyService companyService;
    private final CompanyMapper companyMapper;

    @PreAuthorize("hasRole('COMPANY')")
    @PostMapping()
    public ResponseEntity<CompanyAndUserDTO> create(@RequestBody @Valid CreateCompanyDTO payload) {
        Company companyEntity = this.companyMapper.toCompanyEntity(payload);
        Company company = this.companyService.create(companyEntity);
        CompanyAndUserDTO companyAndUserDTO = this.companyMapper.toCompanyAndUserDTO(company);
        return ResponseEntity.status(HttpStatus.CREATED).body(companyAndUserDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyAndUserDTO> getById(@PathVariable String id) {
        Company company = this.companyService.getById(id);
        CompanyAndUserDTO companyAndUserDTO = this.companyMapper.toCompanyAndUserDTO(company);
        return ResponseEntity.status(HttpStatus.OK).body(companyAndUserDTO);
    }

    @PreAuthorize("hasRole('COMPANY')")
    @PatchMapping()
    public ResponseEntity<CompanyAndUserDTO> update(@RequestBody @Valid UpdateCompanyDTO payload) {
        Company companyEntity = this.companyMapper.toCompanyEntity(payload);
        Company company = this.companyService.update(companyEntity);
        CompanyAndUserDTO companyAndUserDTO = this.companyMapper.toCompanyAndUserDTO(company);
        return ResponseEntity.status(HttpStatus.OK).body(companyAndUserDTO);
    }

    @PreAuthorize("hasRole('COMPANY')")
    @DeleteMapping()
    public ResponseEntity<Void> delete() {
        this.companyService.delete();
        return ResponseEntity.noContent().build();
    }
}
