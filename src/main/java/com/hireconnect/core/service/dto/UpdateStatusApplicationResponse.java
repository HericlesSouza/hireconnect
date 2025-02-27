package com.hireconnect.core.service.dto;

import com.hireconnect.core.entity.Contract;
import com.hireconnect.core.entity.JobVacanciesApplication;

public record UpdateStatusApplicationResponse(JobVacanciesApplication application, Contract contract) {
}