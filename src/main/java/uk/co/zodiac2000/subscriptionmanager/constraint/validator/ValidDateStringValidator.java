package uk.co.zodiac2000.subscriptionmanager.constraint.validator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import uk.co.zodiac2000.subscriptionmanager.constraint.ValidDateString;

/**
 * Validator for ValidDateString.
 */
public class ValidDateStringValidator implements ConstraintValidator<ValidDateString, Optional<String>> {

    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ISO_LOCAL_DATE;

    /**
     * Verifies a date string is valid. If the {@code Optional} contains a value then it must be a valid date string
     * in {@link java.time.format.DateTimeFormatter#ISO_LOCAL_DATE} format. An empty {@code Optional} is also considered
     * valid.
     * @param value the date string to validate
     * @param context the context in which the object is validated
     * @return true if the date string is valid
     */
    @Override
    public boolean isValid(final Optional<String> value, final ConstraintValidatorContext context) {
        try {
            Optional<LocalDate> date = value.map(v -> LocalDate.parse(v, FORMAT));
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }
}
