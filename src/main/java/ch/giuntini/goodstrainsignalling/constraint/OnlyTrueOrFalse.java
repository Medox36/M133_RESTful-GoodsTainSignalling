package ch.giuntini.goodstrainsignalling.constraint;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * constraint that checks a String for a valid boolean value
 *
 * (The problem is that the normal String to Boolean converter just checks
 * if the String value is "true" or "TRUE" and all other values just turn false even
 * if the String contains something like "iueuerboiuergouergbo")
 *
 * Validator:
 * @see BooleanValidatorFromString
 *
 * @author Lorenzo Giuntini (Medox36)
 * @since 2022.06.12
 * @version 1.0
 */
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(OnlyTrueOrFalse.List.class)
@Documented
@Constraint(validatedBy = BooleanValidatorFromString.class)
public @interface OnlyTrueOrFalse {
    String message() default "Can only be false or true, also accepted FALSE or TRUE";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        OnlyTrueOrFalse[] value();
    }
}
