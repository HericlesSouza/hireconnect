package com.hireconnect.adapters.dto.auth;

import com.hireconnect.adapters.dto.user.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthLoginResponseDTO {
    private String token;
    private UserDTO user;
    private Date expiresAt;
}
