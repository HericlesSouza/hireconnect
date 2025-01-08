package com.hireconnect.adapters.dto.user;

import com.hireconnect.core.entity.TypeUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private UUID id;
    private String name;
    private String email;
    private String imgUrl;
    private TypeUser typeUser;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
