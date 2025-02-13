package com.hireconnect.core.service;

import com.hireconnect.adapters.mapper.ModelMapperUtils;
import com.hireconnect.core.entity.Freelancer;
import com.hireconnect.core.entity.User;
import com.hireconnect.core.exception.EmailAlreadyExistsException;
import com.hireconnect.core.exception.ResourceNotFoundException;
import com.hireconnect.core.repository.UserRepository;
import com.hireconnect.infra.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final SecurityUtils securityUtils;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User update(User payload, Freelancer freelancer) {
        UUID userId = this.securityUtils.getAuthenticatedUser().getId();

        User managedUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found."));

        if (!payload.getEmail().equalsIgnoreCase(managedUser.getEmail())) {
            this.validateEmailDoesNotExist(payload.getEmail());
        }

        ModelMapperUtils.mapNonNullProperties(payload, managedUser);

        if (payload.getPassword() != null) {
            managedUser.setPassword(passwordEncoder.encode(payload.getPassword()));
        }

        if (freelancer != null) {
            if (managedUser.getFreelancer() != null) {
                Freelancer managedFreelancer = managedUser.getFreelancer();

                managedFreelancer.setSpecialization(freelancer.getSpecialization());
                managedFreelancer.setBio(freelancer.getBio());
            } else {
                Freelancer newFreelancer = new Freelancer();
                ModelMapperUtils.mapNonNullProperties(freelancer, newFreelancer);
                managedUser.setFreelancer(newFreelancer);
            }
        } else {
            managedUser.removeFreelancer();
        }

        return userRepository.save(managedUser);
    }

    public void validateEmailDoesNotExist(String email) {
        if (this.userRepository.existsByEmail(email)) {
            throw new EmailAlreadyExistsException("The email " + email + " already exists.");
        }
    }
}
