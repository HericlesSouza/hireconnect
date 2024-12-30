package com.hireconnect.presentation.mapper;

import com.hireconnect.core.entity.Company;
import com.hireconnect.presentation.dto.company.CompanyAndUserDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CompanyMapper {
    private final ModelMapper modelMapper;

    public CompanyAndUserDTO toCompanyAndUserDTO(Company company) {
        return this.modelMapper.map(company, CompanyAndUserDTO.class);
    }

    public <T> Company toCompanyEntity(T companyDTO) {
        return this.modelMapper.map(companyDTO, Company.class);
    }
}