package nitin.gupta.room.no13.coreJavaPrectise.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = StringConstraintValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface NumberConstraint {
    String message() default "Invalid number";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
