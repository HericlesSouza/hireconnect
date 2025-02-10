package com.hireconnect.core.entity;

import com.hireconnect.core.exception.BusinessException;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "job_vacancies_applications")
@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = {"jobVacancy", "freelancer"})
public class JobVacanciesApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private UUID id;

    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @NotNull
    @Column(name = "status", nullable = false)
    private JobApplicationsStatus status;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "freelancer_id", nullable = false)
    private Freelancer freelancer;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "job_vacancy_id", nullable = false)
    private JobVacancies jobVacancy;

    @Column(nullable = false, name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public void setApplication(JobVacancies jobVacancy, Freelancer freelancer) {
        if (jobVacancy == null || freelancer == null) {
            throw new BusinessException("Job vacancy and freelancer must not be null.");
        }

        if (this.jobVacancy != null || this.freelancer != null) {
            throw new BusinessException("Application is already linked to a job vacancy and freelancer.");
        }

        this.setStatus(JobApplicationsStatus.PENDING);
        freelancer.addApplication(this);
        jobVacancy.addApplication(this);
    }
}
