package com.hireconnect.core.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = {"department", "applications"})
@NoArgsConstructor
@Entity
@Table(name = "job_vacancies")
public class JobVacancies {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private UUID id;

    @Size(max = 50)
    @Column(name = "title", nullable = false, length = 50)
    private String title;

    @Size(max = 2000)
    @Column(name = "description", nullable = false, length = 2000, columnDefinition = "TEXT")
    private String description;

    @Column(name = "is_active", nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean isActive = true;

    @Column(nullable = false, name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @OneToMany(mappedBy = "jobVacancy", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JobVacanciesApplication> applications = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public JobVacancies(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public JobVacancies(String title, String description, boolean isActive) {
        this.title = title;
        this.description = description;
        this.isActive = isActive;
    }

    public void setDepartment(Department department) {
        if (this.department != department) {
            if (this.department != null) {
                this.department.getJobVacancies().remove(this);
            }

            this.department = department;

            if (department != null && !department.getJobVacancies().contains(this)) {
                department.getJobVacancies().add(this);
            }
        }
    }

    public void removeDepartment() {
        if (this.department == null) return;
        this.department.getJobVacancies().remove(this);
        this.department = null;
    }
}
