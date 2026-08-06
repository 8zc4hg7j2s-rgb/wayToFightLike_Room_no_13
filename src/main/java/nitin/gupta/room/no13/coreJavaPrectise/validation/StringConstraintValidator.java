package nitin.gupta.room.no13.coreJavaPrectise.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class StringConstraintValidator implements ConstraintValidator<NumberConstraint, List<String>> {

    @Override
    public void initialize(NumberConstraint constraintAnnotation) {
        // You can use this method to initialize the validator with the annotation attributes
    }

    @Override
    public boolean isValid(List<String> value, ConstraintValidatorContext context) {
        // You can use this method to validate the list elements
        if (value == null || value.isEmpty()) {
            System.out.println("nusst not null");
            return true; // You can change this to false if you want to reject null or empty lists
        }
        for (String s : value) {
            try {
                Double.parseDouble(s); // Try to parse the string as a double
            } catch (NumberFormatException e) {
                return false; // If the string is not a valid number, return false
            }
        }
        return true; // If all the strings are valid numbers, return true
    }
}
