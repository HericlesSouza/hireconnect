package com.hireconnect.adapters.dto.jobVacancies;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateStatusApplicationDTO {
    private ApplicationDTO application;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ApplicationDTO {
        private JobDTO job;
        private ContractDTO contract;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class JobDTO {
        private UUID id;
        private String title;
        private String status;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ContractDTO {
        private UUID id;
        private String freelancerId;
        private boolean isActive;
    }
}
