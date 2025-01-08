package com.hireconnect.adapters.dto.company;

import com.hireconnect.adapters.dto.user.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyAndUserDTO extends CompanyDTO{
    private UserDTO owner;
}
