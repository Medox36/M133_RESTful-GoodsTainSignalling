package ch.giuntini.goodstrainsignalling.constraint;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = BooleanValidatorFromString.class)
public @interface OnlyTrueOrFalse {
    String message() default "Can only be false or true, also accepted FALSE or TRUE";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}