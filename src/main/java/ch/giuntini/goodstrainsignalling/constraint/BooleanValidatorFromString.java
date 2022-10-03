package ch.giuntini.goodstrainsignalling.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * ConstraintValidator for the constraint OnlyTrueOrFalse
 * @see OnlyTrueOrFalse
 * @see javax.validation.ConstraintValidator
 *
 * @author Lorenzo Giuntini (Medox36)
 * @since 2022.06.12
 * @version 1.1
 */
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
        if (string == null) {
            return false;
        }
        return string.matches("^(true|false|TRUE|FALSE)$");
    }
}
