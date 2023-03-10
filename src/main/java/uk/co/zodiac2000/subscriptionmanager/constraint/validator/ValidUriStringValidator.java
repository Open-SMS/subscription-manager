package uk.co.zodiac2000.subscriptionmanager.constraint.validator;

import java.net.URI;
import java.net.URISyntaxException;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import uk.co.zodiac2000.subscriptionmanager.constraint.ValidUriString;

/**
 * Validator used by ValidUriString constraint.
 */
public class ValidUriStringValidator implements ConstraintValidator<ValidUriString, String> {

    /**
     * Verifies the string contains a valid absolute URI.
     * @param value the string to validate
     * @param context the context in which the object is validated
     * @return true if the string is a valid URI
     */
    @Override
    public boolean isValid(final String value, final ConstraintValidatorContext context) {
        URI uri;
        try {
            uri = new URI(value);
        } catch (URISyntaxException e) {
            return false;
        }
        return uri.isAbsolute();
    }
}
