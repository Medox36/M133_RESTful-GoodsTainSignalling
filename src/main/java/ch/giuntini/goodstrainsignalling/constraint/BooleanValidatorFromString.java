package ch.giuntini.goodstrainsignalling.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class BooleanValidatorFromString implements ConstraintValidator<OnlyTrueOrFalse, String> {

    @Override
    public boolean isValid(String string, ConstraintValidatorContext constraintValidatorContext) {
        return string.matches("^(true|false|TRUE|FALSE)$");
    }
}
