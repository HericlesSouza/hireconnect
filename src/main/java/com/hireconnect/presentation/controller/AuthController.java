package com.hireconnect.presentation.controller;

import com.hireconnect.core.entity.User;
import com.hireconnect.core.service.AuthService;
import com.hireconnect.presentation.dto.auth.AuthLoginDTO;
import com.hireconnect.presentation.dto.auth.AuthLoginResponseDTO;
import com.hireconnect.presentation.dto.user.UserCreateDTO;
import com.hireconnect.presentation.dto.user.UserDTO;
import com.hireconnect.presentation.mapper.UserMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    private final UserMapper userMapper;

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody @Valid UserCreateDTO payload) {
        User user = this.userMapper.toUserEntity(payload);
        User userRegistered = this.authService.register(user);
        UserDTO userDTO = this.userMapper.toUserDTO(userRegistered);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthLoginResponseDTO> login(@RequestBody @Valid AuthLoginDTO payload) {
        Map<String, Object> loginResponse = this.authService.login(payload.getEmail(), payload.getPassword());

        UserDTO userDTO = this.userMapper.toUserDTO((User) loginResponse.get("user"));
        AuthLoginResponseDTO response = new AuthLoginResponseDTO(
                (String) loginResponse.get("token"),
                userDTO,
                (Date) loginResponse.get("expiresAt")
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/me")
    public ResponseEntity<UserDTO> getUserDetails(Authentication authentication) {
        User user = this.authService.getUserDetails(authentication);
        UserDTO userDTO = this.userMapper.toUserDTO(user);
        return ResponseEntity.status(HttpStatus.OK).body(userDTO);
    }
}
