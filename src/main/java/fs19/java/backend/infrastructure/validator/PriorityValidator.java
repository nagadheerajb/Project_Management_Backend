package fs19.java.backend.infrastructure.validator;

import fs19.java.backend.domain.entity.enums.Priority;
import fs19.java.backend.domain.entity.enums.ValidPriority;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PriorityValidator implements ConstraintValidator<ValidPriority, String> {

    @Override
    public void initialize(ValidPriority constraintAnnotation) {
        // Initialization logic (optional)
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return false; // Or true if you want to allow null or empty values
        }

        try {
            // Check if the value exists in the Priority enum
            Priority.fromName(value); // Or use fromId(int id) if working with numeric values
            return true;
        } catch (IllegalArgumentException e) {
            return false; // Invalid priority name or id
        }
    }
}
