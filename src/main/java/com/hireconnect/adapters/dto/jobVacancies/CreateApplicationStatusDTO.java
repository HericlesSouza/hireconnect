package com.hireconnect.adapters.dto.jobVacancies;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateApplicationStatusDTO {
    @NotBlank(message = "status is required.")
    private String status;
}
