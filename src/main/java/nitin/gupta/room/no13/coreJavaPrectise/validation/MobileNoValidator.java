package nitin.gupta.room.no13.coreJavaPrectise.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MobileNoValidator implements ConstraintValidator<MobileValidation, Integer> {


    @Override
    public void initialize(MobileValidation constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Integer integer, ConstraintValidatorContext constraintValidatorContext) {
        return false;
    }
}
