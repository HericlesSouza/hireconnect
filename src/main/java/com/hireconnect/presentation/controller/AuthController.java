package com.hireconnect.presentation.controller;

import com.hireconnect.adapters.dto.auth.AuthLoginDTO;
import com.hireconnect.adapters.dto.auth.AuthLoginResponseDTO;
import com.hireconnect.adapters.dto.user.UserCreateDTO;
import com.hireconnect.adapters.dto.user.UserDTO;
import com.hireconnect.adapters.dto.user.UserWithRelationsDTO;
import com.hireconnect.adapters.mapper.Mapper;
import com.hireconnect.core.entity.User;
import com.hireconnect.core.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    private final Mapper mapper;

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody @Valid UserCreateDTO payload) {
        User user = this.mapper.map(payload, User.class);
        User userRegistered = this.authService.register(user);
        UserDTO userDTO = this.mapper.map(userRegistered, UserDTO.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthLoginResponseDTO> login(@RequestBody @Valid AuthLoginDTO payload) {
        Map<String, Object> loginResponse = this.authService.login(payload.getEmail(), payload.getPassword());

        UserDTO userDTO = this.mapper.map((User) loginResponse.get("user"), UserDTO.class);
        AuthLoginResponseDTO response = new AuthLoginResponseDTO(
                (String) loginResponse.get("token"),
                userDTO,
                (Date) loginResponse.get("expiresAt")
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/me")
    public ResponseEntity<UserWithRelationsDTO> getUserDetails() {
        User user = this.authService.getUserDetails();
        UserWithRelationsDTO userWithRelationsDTO = this.mapper.map(user, UserWithRelationsDTO.class);
        return ResponseEntity.status(HttpStatus.OK).body(userWithRelationsDTO);
    }
}
