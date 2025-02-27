package com.hireconnect.core.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@Entity
@Table(name = "contracts")
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private UUID id;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(nullable = false, name = "job_vacancy_id")
    private JobVacancies jobVacancy;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(nullable = false, name = "freelancer_id")
    private Freelancer freelancer;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(nullable = false, name = "department_id")
    private Department department;

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

    public Contract(boolean isActive, JobVacancies jobVacancy, Freelancer freelancer, Department department) {
        this.isActive = isActive;
        this.jobVacancy = jobVacancy;
        this.freelancer = freelancer;
        this.department = department;
    }

    public void setJobVacancy(JobVacancies jobVacancy) {
        if (this.jobVacancy != jobVacancy) {
            if (this.jobVacancy != null) {
                this.jobVacancy.getContracts().remove(this);
            }
            this.jobVacancy = jobVacancy;
            if (jobVacancy != null && !jobVacancy.getContracts().contains(this)) {
                jobVacancy.getContracts().add(this);
            }
        }
    }

    public void setFreelancer(Freelancer freelancer) {
        if (this.freelancer != freelancer) {
            if (this.freelancer != null) {
                this.freelancer.getContracts().remove(this);
            }
            this.freelancer = freelancer;
            if (freelancer != null && !freelancer.getContracts().contains(this)) {
                freelancer.getContracts().add(this);
            }
        }
    }

    public void setDepartment(Department department) {
        if (this.department != department) {
            if (this.department != null) {
                this.department.getContracts().remove(this);
            }
            this.department = department;
            if (department != null && !department.getContracts().contains(this)) {
                department.getContracts().add(this);
            }
        }
    }
}
