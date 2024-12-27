package com.hireconnect.core.annotation.optionalNotBlank;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.StringUtils;

public class OptionalNotBlankValidator implements ConstraintValidator<OptionalNotBlank, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value == null || StringUtils.hasText(value);
    }
}
