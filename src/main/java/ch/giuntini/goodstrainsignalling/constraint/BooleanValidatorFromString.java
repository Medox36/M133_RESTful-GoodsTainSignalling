package ch.giuntini.goodstrainsignalling.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class BooleanValidatorFromString implements ConstraintValidator<OnlyTrueOrFalse, String> {

    /**
     * checks if the given value of type String is a valid boolean value
     *
     * @param string value to be checked
     * @param constraintValidatorContext
     * @return if string is valid boolean value
     */
    @Override
    public boolean isValid(String string, ConstraintValidatorContext constraintValidatorContext) {
        return string.matches("^(true|false|TRUE|FALSE)$");
    }
}