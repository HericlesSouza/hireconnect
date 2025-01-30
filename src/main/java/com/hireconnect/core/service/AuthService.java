package com.hireconnect.core.service;

import com.hireconnect.core.entity.Freelancer;
import com.hireconnect.core.entity.User;
import com.hireconnect.core.exception.AuthenticationException;
import com.hireconnect.core.exception.EmailAlreadyExistsException;
import com.hireconnect.core.repository.UserRepository;
import com.hireconnect.infra.security.SecurityUtils;
import com.hireconnect.infra.security.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final SecurityUtils securityUtils;

    public User register(User payload, Freelancer freelancerPayload) {
        this.validateEmailDoesNotExist(payload.getEmail());

        String passwordEncrypted = this.passwordEncoder.encode(payload.getPassword());
        User user = new User(payload.getName(), payload.getEmail(), passwordEncrypted, payload.getImgUrl(), payload.getTypeUser());

        if (payload.getTypeUser().name().equalsIgnoreCase("FREELANCER")) {
            Freelancer freelancer = new Freelancer(freelancerPayload.getSpecialization(), freelancerPayload.getBio());

            user.setFreelancer(freelancer);
        }

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

    public User getUserDetails() {
        return this.securityUtils.getAuthenticatedUser();
    }

    public void validateEmailDoesNotExist(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new EmailAlreadyExistsException("The email " + email + " already exists.");
        }
    }
}
