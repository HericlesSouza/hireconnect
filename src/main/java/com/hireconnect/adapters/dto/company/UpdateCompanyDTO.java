package com.hireconnect.adapters.dto.company;

import com.hireconnect.core.annotation.optionalNotBlank.OptionalNotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCompanyDTO {
    @Size(max = 50, message = "the size must be less than 50 characters.")
    @OptionalNotBlank(message = "name must not be blank.")
    private String name;

    @Size(max = 200, message = "the size must be less than 200 characters.")
    @OptionalNotBlank(message = "description must not be blank.")
    private String description;
}
