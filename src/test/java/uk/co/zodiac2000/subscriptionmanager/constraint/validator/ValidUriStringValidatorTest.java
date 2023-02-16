package uk.co.zodiac2000.subscriptionmanager.constraint.validator;

import javax.validation.ConstraintValidatorContext;
import org.mockito.Mock;
import org.mockito.testng.MockitoTestNGListener;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

/**
 * Unit tests for ValidUriStringValidator.
 */
@Listeners(MockitoTestNGListener.class)
public class ValidUriStringValidatorTest {

    @Mock(lenient = true)
    private ConstraintValidatorContext validatorContext;

    private final ValidUriStringValidator validator = new ValidUriStringValidator();

    /**
     * Test isValid when the string is a valid URI.
     */
    @Test
    public void testIsValid() {
        Assert.assertTrue(this.validator.isValid("https://example.com", this.validatorContext));
    }

    /**
     * Test isValid when the string is not a valid absolute URI because it is not absolute.
     */
    @Test
    public void testIsValidNotValid() {
        Assert.assertFalse(this.validator.isValid("www.example.com", this.validatorContext));
    }

    /**
     * Test isValid when the string is not a valid absolute URI because the syntax is invalid.
     */
    @Test
    public void testIsValidNotValidSyntax() {
        Assert.assertFalse(this.validator.isValid("https//?/foo", this.validatorContext));
    }
}
