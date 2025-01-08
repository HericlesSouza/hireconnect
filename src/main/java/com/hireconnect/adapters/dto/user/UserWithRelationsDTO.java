package com.hireconnect.adapters.dto.user;

import com.hireconnect.adapters.dto.company.CompanyDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserWithRelationsDTO extends UserDTO {
    private CompanyDTO company;
}
