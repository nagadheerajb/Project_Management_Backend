package fs19.java.backend.infrastructure.validator;

import fs19.java.backend.domain.entity.enums.TaskStatus;
import fs19.java.backend.domain.entity.enums.ValidTaskStatus;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TaskValidator implements ConstraintValidator<ValidTaskStatus, String> {

    @Override
    public void initialize(ValidTaskStatus constraintAnnotation) {
        // Initialization logic (optional)
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return false;
        }

        try {
            TaskStatus.fromName(value);
            return true;
        } catch (IllegalArgumentException e) {
            return false; // Invalid priority name or id
        }
    }
}
