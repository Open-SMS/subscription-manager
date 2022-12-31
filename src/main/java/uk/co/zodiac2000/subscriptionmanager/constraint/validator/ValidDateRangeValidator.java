package uk.co.zodiac2000.subscriptionmanager.constraint.validator;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.apache.commons.beanutils.PropertyUtils;
import uk.co.zodiac2000.subscriptionmanager.constraint.ValidDateRange;

/**
 * Validator for {@code ValidDateRange}.
 */
public class ValidDateRangeValidator implements ConstraintValidator<ValidDateRange, Object> {

    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ISO_LOCAL_DATE;

    private String firstDatePropertyName;

    private String secondDatePropertyName;

    private String message;

    /**
     * Initializes the validator.
     * @param constraintAnnotation the constraint annotation
     */
    @Override
    public void initialize(final ValidDateRange constraintAnnotation) {
        this.firstDatePropertyName = constraintAnnotation.firstDatePropertyName();
        this.secondDatePropertyName = constraintAnnotation.secondDatePropertyName();
        this.message = constraintAnnotation.message();
    }

    /**
     * Returns true if the property named {@code firstDatePropertyName} is an {@code Optional} containing
     * a valid date string and the property named {@code secondDatePropertyName} is an {@code Optional} containing
     * a valid date string, and the the first date occurs before or on the same date as the second date. Also returns
     * true if either property does not exist, is empty, is not an {@code Optional}, or the date string cannot be
     * parsed. It is assumed that other validation will handle incorrectly formatted dates. If either date is empty
     * then the date range is valid. If not valid then the constraint violation is set on the property named
     * {@code firstDatePropertyName}.
     * @param value the object to verify
     * @param context context the context in which the object is validated
     * @return true if the date range is valid
     */
    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context) {
        Optional<LocalDate> firstDate = getPropertyValueAsLocalDate(value, this.firstDatePropertyName);
        Optional<LocalDate> secondDate = getPropertyValueAsLocalDate(value, this.secondDatePropertyName);
        boolean valid = firstDate.map(s ->
                secondDate.map(e -> e.isAfter(s) || e.equals(s)).orElse(true)
        ).orElse(true);

        if (!valid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(this.message)
                    .addPropertyNode(this.firstDatePropertyName)
                    .addConstraintViolation();
        }

        return valid;
    }

    /**
     * Returns an {@code Optional} containing a LocalDate parsed from the property of {@code targetObject}
     * named {@code propertyName}. If the property is not accessible, is not an optional, or the date string cannot
     * be parsed then an empty optional is returned.
     * @param targetObject the object to obtain the property value from
     * @param propertyName the property name
     * @return an optional containing the property value
     */
    private Optional<LocalDate> getPropertyValueAsLocalDate(final Object targetObject, final String propertyName) {
        try {
            Optional<String> dateString = (Optional) PropertyUtils.getProperty(targetObject, propertyName);
            return dateString.map(v -> LocalDate.parse(v, FORMAT));
        } catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException
                | DateTimeParseException | ClassCastException e) {
            return Optional.empty();
        }
    }
}
