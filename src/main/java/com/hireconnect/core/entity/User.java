package com.hireconnect.core.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = {"company", "freelancer"})
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private UUID id;

    @NotBlank
    @Size(max = 50)
    @Column(nullable = false, length = 50)
    private String name;

    @Email
    @NotBlank
    @Size(max = 50)
    @Column(nullable = false, length = 50, unique = true)
    private String email;

    @NotBlank
    @Column(nullable = false)
    private String password;

    @Size(max = 2000)
    @Column(length = 2000, name = "img_url")
    private String imgUrl;

    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @NotNull
    @Column(nullable = false, name = "type_user")
    private TypeUser typeUser;

    @Column(nullable = false, name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToOne(mappedBy = "owner", cascade = CascadeType.ALL, optional = true, orphanRemoval = true)
    private Company company;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, optional = true, orphanRemoval = true)
    private Freelancer freelancer;

    public User(String name, String email, String password, String imgUrl, TypeUser typeUser) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.imgUrl = imgUrl;
        this.typeUser = typeUser;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
