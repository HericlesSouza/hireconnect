package com.hireconnect.adapters.dto.jobVacancies;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateJobVacanciesDTO {
    @Size(max = 50, message = "the size must be less than 50 characters.")
    @NotBlank(message = "title is required.")
    private String title;

    @Size(max = 2000, message = "the size must be less than 2000 characters.")
    @NotBlank(message = "description is required.")
    private String description;
}
