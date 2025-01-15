package com.hexaware.QuitQApplication.validation;

import com.hexaware.QuitQApplication.dto.UserDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UserDTOValidator implements ConstraintValidator<ValidUserDTO, UserDTO> {

    @Override
    public void initialize(ValidUserDTO constraintAnnotation) {
    }

    @Override
    public boolean isValid(UserDTO userDto, ConstraintValidatorContext context) {
        if (userDto == null) {
            return false; // If userDto is null, validation fails
        }
        // Add custom logic to validate userDto fields
        if (userDto.getUsername() == null || userDto.getUsername().isEmpty()) {
            return false;
        }
        // Additional validations can be added for other fields of UserDTO
        return true;
    }
}
