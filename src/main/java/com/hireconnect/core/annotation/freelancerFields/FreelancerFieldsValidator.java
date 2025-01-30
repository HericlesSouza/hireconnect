package com.hireconnect.core.annotation.freelancerFields;

import com.hireconnect.adapters.dto.user.UserCreateDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.HashMap;
import java.util.Map;

public class FreelancerFieldsValidator implements ConstraintValidator<FreelancerFields, UserCreateDTO> {

    @Override
    public boolean isValid(UserCreateDTO user, ConstraintValidatorContext context) {
        if (user == null || !"FREELANCER".equalsIgnoreCase(user.getTypeUser())) {
            return true;
        }

        Map<String, String> errors = new HashMap<>();

        if (isBlank(user.getSpecialization())) {
            errors.put("specialization", "For FREELANCER users, 'specialization' is required.");
        }

        if (isBlank(user.getBio())) {
            errors.put("bio", "For FREELANCER users, 'bio' is required.");
        }

        if (errors.isEmpty()) {
            return true;
        }

        context.disableDefaultConstraintViolation();
        errors.forEach((field, message) ->
                context.buildConstraintViolationWithTemplate(message)
                        .addPropertyNode(field)
                        .addConstraintViolation()
        );

        return false;
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
