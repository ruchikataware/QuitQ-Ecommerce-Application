package com.hexaware.QuitQApplication.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// Custom annotation
@Constraint(validatedBy = UserDTOValidator.class)
@Target({ ElementType.FIELD, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidUserDTO {
    String message() default "Invalid user details.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
