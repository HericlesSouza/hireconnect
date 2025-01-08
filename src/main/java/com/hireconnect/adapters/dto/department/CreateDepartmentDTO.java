package com.hireconnect.adapters.dto.department;

import com.hireconnect.core.annotation.optionalNotBlank.OptionalNotBlank;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateDepartmentDTO {
    @Size(max = 50, message = "the size must be less than 50 characters.")
    @NotBlank(message = "name is required.")
    private String name;

    @Size(max = 2000, message = "the size must be less than 2000 characters.")
    @OptionalNotBlank(message = "description is required.")
    private String description;
}
