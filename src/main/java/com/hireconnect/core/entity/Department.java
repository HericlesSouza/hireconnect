package com.hireconnect.core.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "departments")
@NoArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = {"company", "jobVacancies"})
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private UUID id;

    @NotBlank
    @Size(max = 50)
    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @NotBlank
    @Size(max = 2000)
    @Column(name = "description", nullable = false, length = 2000, columnDefinition = "TEXT")
    private String description;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(nullable = false, name = "company_id")
    private Company company;

    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JobVacancies> jobVacancies = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public Department(String name, String description, Company company) {
        this.name = name;
        this.description = description;
        this.company = company;
    }

    public void setCompany(Company company) {
        if (this.company != company) {
            if (this.company != null) {
                this.company.getDepartments().remove(this);
            }
            this.company = company;
            if (company != null && !company.getDepartments().contains(this)) {
                company.getDepartments().add(this);
            }
        }
    }

    public void removeCompany() {
        if (this.company == null) return;
        this.company.getDepartments().remove(this);
        this.company = null;
    }

    public void addJobVacancies(JobVacancies jobVacancies) {
        if (jobVacancies == null || this.jobVacancies.contains(jobVacancies)) return;
        this.jobVacancies.add(jobVacancies);
        jobVacancies.setDepartment(this);
    }

    public void removeJobVacancies(JobVacancies jobVacancies) {
        if (jobVacancies != null && this.jobVacancies.remove(jobVacancies)) {
            jobVacancies.setDepartment(null);
        }
    }
}
