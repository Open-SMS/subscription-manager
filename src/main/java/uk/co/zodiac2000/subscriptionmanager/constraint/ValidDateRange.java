package uk.co.zodiac2000.subscriptionmanager.constraint;

import java.lang.annotation.Documented;
import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import uk.co.zodiac2000.subscriptionmanager.constraint.validator.ValidDateRangeValidator;

/**
 * Constraint that is used to verify the date represented by the {@code Optional<String>} identified by
 * {@code firstDatePropertyName} occurs before or on the same date as the {@code Optional<String>} identified by
 * {@code secondDatePropertyName}. If either of the optionals are empty or the strings cannot be parsed then the
 * date range is considered valid.
 */
@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = ValidDateRangeValidator.class)
@Documented
public @interface ValidDateRange {

    /**
     * Returns the default message. By default the fully qualified interface name is used.
     * @return default message.
     */
    String message() default "{uk.co.zodiac2000.subscriptionmanager.constraint.ValidDateRange}";

    /**
     * Returns the default validation groups. There are no default validation groups for this constraint.
     * @return default validation groups. 
     */
    Class<?>[] groups() default {};

    /**
     * Returns the payload. No payload is defined by default for this validator.
     * @return the payload
     */
    Class<? extends Payload>[] payload() default {};

    /**
     * Returns the property name of the first date.
     * @return the property name of the first date
     */
    String firstDatePropertyName();

    /**
     * Returns the property name of the second date.
     * @return the property name of the second date
     */
    String secondDatePropertyName();
}
