package com.hireconnect.core.useCase;

import com.hireconnect.core.entity.User;
import com.hireconnect.core.exception.AuthenticationException;
import com.hireconnect.core.exception.EmailAlreadyExistsException;
import com.hireconnect.core.exception.ResourceNotFoundException;
import com.hireconnect.core.repository.UserRepository;
import com.hireconnect.infra.security.TokenService;
import jakarta.security.auth.message.AuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class AuthUseCase {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public User register(User payload) {
        this.validateEmailDoesNotExist(payload.getEmail());

        String passwordEncrypted = this.passwordEncoder.encode(payload.getPassword());
        User user = new User(payload.getName(), payload.getEmail(), passwordEncrypted, payload.getImgUrl(), payload.getTypeUser());
        this.userRepository.save(user);
        return user;
    }

    public Map<String, Object> login(String email, String password) {
        User user = this.userRepository.findByEmail(email).orElseThrow(() -> new AuthenticationException("Invalid credentials."));

        if (passwordEncoder.matches(password, user.getPassword())) {
            String token = this.tokenService.generateToken(user);

            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("user", user);
            response.put("expiresAt", tokenService.getTokenExpiration(token)); // Obtém a expiração do token

            return response;
        }

        throw new AuthenticationException("Invalid credentials.");
    }

    public User getUserDetails(Authentication authentication) {
        return (User) authentication.getPrincipal();
    }

    public void validateEmailDoesNotExist(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new EmailAlreadyExistsException("The email " + email + " already exists.");
        }
    }
}
