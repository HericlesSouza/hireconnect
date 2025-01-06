package com.hireconnect.core.annotation.validUUID;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UUIDValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidUUID {
    String message() default "must be a valid UUID.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
