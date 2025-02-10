package com.hireconnect.adapters.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobApplyResponseDTO {
    JobDTO job;
    FreelancerDTO freelancer;
    LocalDateTime applicationDate;

    @Data
    @AllArgsConstructor
    public static class JobDTO {
        private UUID id;
        private String title;
        private String description;
    }

    @Data
    @AllArgsConstructor
    public static class FreelancerDTO {
        private UUID id;
        private String name;
        private String email;
    }
}