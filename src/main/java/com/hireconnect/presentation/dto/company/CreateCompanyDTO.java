package com.hireconnect.presentation.dto.company;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCompanyDTO {
    @Size(max = 50, message = "the size must be less than 50 characters.")
    @NotBlank(message = "name is required.")
    private String name;

    @Size(max = 200, message = "the size must be less than 200 characters.")
    @NotBlank(message = "description is required.")
    private String description;
}
