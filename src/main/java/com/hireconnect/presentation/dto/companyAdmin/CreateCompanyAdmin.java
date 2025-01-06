package com.hireconnect.presentation.dto.companyAdmin;

import com.hireconnect.core.annotation.validUUID.ValidUUID;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCompanyAdmin {
    @ValidUUID(message = "The userId must be a valid UUID.")
    @NotBlank(message = "The userId is required.")
    private String userId;
}
