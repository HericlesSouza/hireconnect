package com.hireconnect.presentation.controller;

import com.hireconnect.core.entity.User;
import com.hireconnect.core.useCase.AuthUseCase;
import com.hireconnect.presentation.dto.user.UserCreateDTO;
import com.hireconnect.presentation.dto.user.UserDTO;
import com.hireconnect.presentation.mapper.UserMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthUseCase authUseCase;
    private final UserMapper userMapper;

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody @Valid UserCreateDTO payload) {
        User user = this.userMapper.toUserEntity(payload);
        User userRegistered = this.authUseCase.register(user);
        UserDTO userDTO = this.userMapper.toUserDTO(userRegistered);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDTO);
    }
}
