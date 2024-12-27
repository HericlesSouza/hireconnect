package com.hireconnect.core.useCase;

import com.hireconnect.core.entity.User;
import com.hireconnect.core.exception.EmailAlreadyExistsException;
import com.hireconnect.core.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthUseCase {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User register(User payload) {
        this.validateEmailDoesNotExist(payload.getEmail());

        String passwordEncrypted = this.passwordEncoder.encode(payload.getPassword());
        User user = new User(payload.getName(), payload.getEmail(), passwordEncrypted, payload.getImgUrl(), payload.getTypeUser());
        this.userRepository.save(user);
        return user;
    }

    public void validateEmailDoesNotExist(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new EmailAlreadyExistsException("The email " + email + " already exists.");
        }
    }
}
