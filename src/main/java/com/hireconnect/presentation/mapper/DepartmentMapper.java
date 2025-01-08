package com.hireconnect.presentation.mapper;

import com.hireconnect.core.entity.Department;
import com.hireconnect.presentation.dto.department.DepartmentDTO;
import com.hireconnect.presentation.dto.department.DepartmentWithCompanyDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DepartmentMapper {
    private final ModelMapper modelMapper;

    public DepartmentDTO toDepartmentDTO(Department department) {
        return this.modelMapper.map(department, DepartmentDTO.class);
    }

    public DepartmentWithCompanyDTO toDepartmentWithCompanyDTO(Department department) {
        return this.modelMapper.map(department, DepartmentWithCompanyDTO.class);
    }

    public <T> Department toEntity(T object) {
        return this.modelMapper.map(object, Department.class);
    }
}
