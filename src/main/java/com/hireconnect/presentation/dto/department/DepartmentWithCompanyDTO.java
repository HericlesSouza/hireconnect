package com.hireconnect.presentation.dto.department;

import com.hireconnect.presentation.dto.company.CompanyDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentWithCompanyDTO extends DepartmentDTO{
    private CompanyDTO company;
}
