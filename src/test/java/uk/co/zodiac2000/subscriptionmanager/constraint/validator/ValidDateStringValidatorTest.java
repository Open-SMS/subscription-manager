package uk.co.zodiac2000.subscriptionmanager.constraint.validator;

import java.util.Optional;
import javax.validation.ConstraintValidatorContext;
import org.mockito.Mock;
import org.mockito.testng.MockitoTestNGListener;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

/**
 * Unit tests for ValidDateStringValidator.
 */
@Listeners(MockitoTestNGListener.class)
public class ValidDateStringValidatorTest {

    @Mock(lenient = true)
    private ConstraintValidatorContext validatorContext;

    private final ValidDateStringValidator validator = new ValidDateStringValidator();

    /**
     * Test isValid when the date string is a valid date.
     */
    @Test
    public void testIsValid() {
        Assert.assertTrue(this.validator.isValid(Optional.of("2012-02-03"), this.validatorContext));
    }

    /**
     * Test isValid when the {@code Optional} is empty.
     */
    @Test
    public void testIsValidEmpty() {
        Assert.assertTrue(this.validator.isValid(Optional.empty(), this.validatorContext));
    }

    /**
     * Test isValid when the date string is not a valid date.
     */
    @Test
    public void testIsValidNotValid() {
        Assert.assertFalse(this.validator.isValid(Optional.of("2012-XX-XX"), this.validatorContext));
    }
}
