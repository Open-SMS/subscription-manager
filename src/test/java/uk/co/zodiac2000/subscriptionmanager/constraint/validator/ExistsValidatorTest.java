package uk.co.zodiac2000.subscriptionmanager.constraint.validator;

import javax.validation.ConstraintValidatorContext;
import javax.validation.ValidationException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.testng.MockitoTestNGListener;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.test.util.ReflectionTestUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import uk.co.zodiac2000.subscriptionmanager.constraint.Exists;

/**
 * Unit tests for ExistsValidator.
 */
@Listeners(MockitoTestNGListener.class)
public class ExistsValidatorTest {

    private static final String EXPRESSION = "true";
    private static final String MESSAGE = "message";
    private static final long EXISTING_ID = 42L;
    private static final long NOT_EXISTING_ID = 378L;

    @Mock
    private BeanFactory beanFactory;

    @Mock(lenient = true)
    private Expression verificationExpression;

    @Mock
    private StandardEvaluationContext evaluationContext;

    @Mock(lenient = true)
    private Exists constraintAnnotation;

    @Mock(lenient = true)
    private ConstraintValidatorContext validatorContext;

    @InjectMocks
    private ExistsValidator validator;

    @BeforeMethod
    public void setUpVerificationExpression() {
        Mockito.when(this.verificationExpression.getValue(Mockito.eq(this.evaluationContext),
                Mockito.eq(EXISTING_ID), Mockito.eq(Boolean.class)))
                .thenReturn(true);

        Mockito.when(this.verificationExpression.getValue(Mockito.eq(this.evaluationContext),
                Mockito.eq(NOT_EXISTING_ID), Mockito.eq(Boolean.class)))
                .thenReturn(false);
    }

    @BeforeMethod
    public void setUpConstraintAnnotation() {
        Mockito.when(this.constraintAnnotation.expression()).thenReturn(EXPRESSION);
        Mockito.when(this.constraintAnnotation.message()).thenReturn(MESSAGE);
    }

    /**
     * Test initialize.
     */
    @Test
    public void testInitialize() {
        this.validator.initialize(this.constraintAnnotation);

        Assert.assertTrue(
                ReflectionTestUtils.getField(this.validator, "evaluationContext") instanceof StandardEvaluationContext);
        Assert.assertTrue(
                ReflectionTestUtils.getField(this.validator, "verificationExpression") instanceof Expression);
        Assert.assertEquals(
                ((Expression) ReflectionTestUtils.getField(this.validator, "verificationExpression")).getExpressionString(),
                EXPRESSION);
    }

    /**
     * Test isValid when the verificationExpression returns null.
     */
    @Test(expectedExceptions = {ValidationException.class},
            expectedExceptionsMessageRegExp = "^Unexpected null value returned by expression evaluation$")
    public void testIsValidExpressionReturnsNull() {
        this.validator.isValid(null, this.validatorContext);
    }

    /**
     * Test isValid when the verificationExpression returns false.
     */
    @Test
    public void testIsValidExpressionReturnsFalse() {
        Assert.assertFalse(this.validator.isValid(NOT_EXISTING_ID, this.validatorContext));
    }

    /**
     * Test isValid when the verificationExpression returns true.
     */
    @Test
    public void testIsValidExpressionReturnsTrue() {
        Assert.assertTrue(this.validator.isValid(EXISTING_ID, this.validatorContext));
    }
}
