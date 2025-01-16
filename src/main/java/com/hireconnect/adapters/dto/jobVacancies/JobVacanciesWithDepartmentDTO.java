package com.hireconnect.adapters.dto.jobVacancies;

import com.hireconnect.adapters.dto.department.DepartmentDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobVacanciesWithDepartmentDTO extends JobVacanciesDTO {
    DepartmentDTO department;
}
