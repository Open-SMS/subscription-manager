package uk.co.zodiac2000.subscriptionmanager.constraint;

import java.lang.annotation.Documented;
import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import uk.co.zodiac2000.subscriptionmanager.constraint.validator.ValidDateStringValidator;

/**
 * Field constraint that verifies that if the {@code Optional} contains a value then it is a valid date string
 * in {@link java.time.format.DateTimeFormatter#ISO_LOCAL_DATE} format.
 */
@Target({FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = ValidDateStringValidator.class)
@Documented
public @interface ValidDateString {

    /**
     * Returns the default message. By default the fully qualified interface name is used.
     * @return default message.
     */
    String message() default "{uk.co.zodiac2000.subscriptionmanager.constraint.ValidDateString}";

    /**
     * Returns the default validation groups. There are no default validation groups for this constraint.
     * @return default validation groups. 
     */
    Class<?>[] groups() default {};

    /**
     * Returns the payload. No payload is defined by default for this constraint.
     * @return the payload
     */
    Class<? extends Payload>[] payload() default {};
}
