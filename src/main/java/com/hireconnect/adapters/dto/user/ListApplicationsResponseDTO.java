package com.hireconnect.adapters.dto.user;

import com.hireconnect.adapters.dto.company.CompanyDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListApplicationsResponseDTO {
    List<ApplicationDTO> applications;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ApplicationDTO {
        private CompanyDTO company;
        private JobDTO job;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class JobDTO {
        private UUID id;
        private String title;
        private String status;
        private LocalDateTime appliedAt;
    }
}
