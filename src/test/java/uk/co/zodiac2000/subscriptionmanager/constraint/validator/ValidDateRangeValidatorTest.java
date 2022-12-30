package uk.co.zodiac2000.subscriptionmanager.constraint.validator;

import java.util.Optional;
import javax.validation.ConstraintValidatorContext;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.testng.MockitoTestNGListener;
import org.springframework.test.util.ReflectionTestUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import uk.co.zodiac2000.subscriptionmanager.constraint.ValidDateRange;

/**
 * Unit tests for ValidDateRangeValidator.
 */
@Listeners(MockitoTestNGListener.class)
public class ValidDateRangeValidatorTest {

    private static final String FIRST_DATE_PROPERTY_NAME = "startDate";
    private static final String SECOND_DATE_PROPERTY_NAME = "endDate";
    private static final String MESSAGE = "message";

    @Mock(lenient = true)
    private ConstraintValidatorContext context;

    @Mock(lenient = true)
    private ValidDateRange constraintAnnotation;

    @Mock(lenient = true)
    private ConstraintValidatorContext.ConstraintViolationBuilder constraintViolationBuilder;

    @Mock(lenient = true)
    private ConstraintValidatorContext.ConstraintViolationBuilder.NodeBuilderCustomizableContext nodeBuilderCustomizableContext;

    @BeforeMethod
    public void setUpValidatorContext() {
        Mockito.when(this.context.buildConstraintViolationWithTemplate(Mockito.any()))
                .thenReturn(this.constraintViolationBuilder);
        Mockito.when(this.constraintViolationBuilder.addPropertyNode(Mockito.any()))
                .thenReturn(this.nodeBuilderCustomizableContext);
    }

    /**
     * Test initialize.
     */
    @Test
    public void testInitialize() {
        ValidDateRangeValidator validator = new ValidDateRangeValidator();
        Mockito.when(this.constraintAnnotation.firstDatePropertyName()).thenReturn(FIRST_DATE_PROPERTY_NAME);
        Mockito.when(this.constraintAnnotation.secondDatePropertyName()).thenReturn(SECOND_DATE_PROPERTY_NAME);
        Mockito.when(this.constraintAnnotation.message()).thenReturn(MESSAGE);
        validator.initialize(this.constraintAnnotation);

        Assert.assertEquals(ReflectionTestUtils.getField(validator, "firstDatePropertyName"),
                FIRST_DATE_PROPERTY_NAME);
        Assert.assertEquals(ReflectionTestUtils.getField(validator, "secondDatePropertyName"),
                SECOND_DATE_PROPERTY_NAME);
    }

    /**
     * Test isValid when the property named firstDatePropertyName represents a date that occurs before the
     * date represented by the property named secondDatePropertyName.
     */
    @Test
    public void testIsValidBefore() {
        ValidDateRangeValidator validator = getConfiguredValidator();
        TargetObject object = new TargetObject(Optional.of("2021-01-01"), Optional.of("2021-02-01"));

        Assert.assertTrue(validator.isValid(object, this.context));
        Mockito.verify(this.context, Mockito.never()).disableDefaultConstraintViolation();
        Mockito.verify(this.context, Mockito.never()).buildConstraintViolationWithTemplate(Mockito.any());
        Mockito.verify(this.constraintViolationBuilder, Mockito.never()).addPropertyNode(Mockito.any());
        Mockito.verify(this.nodeBuilderCustomizableContext, Mockito.never()).addConstraintViolation();
    }

    /**
     * Test isValid when the property named firstDatePropertyName represents a date that is the same as the
     * date represented by the property named secondDatePropertyName.
     */
    @Test
    public void testIsValidEqual() {
        ValidDateRangeValidator validator = getConfiguredValidator();
        TargetObject object = new TargetObject(Optional.of("2021-02-01"), Optional.of("2021-02-01"));

        Assert.assertTrue(validator.isValid(object, this.context));
        Mockito.verify(this.context, Mockito.never()).disableDefaultConstraintViolation();
        Mockito.verify(this.context, Mockito.never()).buildConstraintViolationWithTemplate(Mockito.any());
        Mockito.verify(this.constraintViolationBuilder, Mockito.never()).addPropertyNode(Mockito.any());
        Mockito.verify(this.nodeBuilderCustomizableContext, Mockito.never()).addConstraintViolation();
    }

    /**
     * Test isValid when the property named firstDatePropertyName represents a date that occurs after the
     * date represented by the property named secondDatePropertyName.
     */
    @Test
    public void testIsValidAfter() {
        ValidDateRangeValidator validator = getConfiguredValidator();
        TargetObject object = new TargetObject(Optional.of("2021-02-02"), Optional.of("2021-02-01"));

        Assert.assertFalse(validator.isValid(object, this.context));
        Mockito.verify(this.context).disableDefaultConstraintViolation();
        Mockito.verify(this.context).buildConstraintViolationWithTemplate(MESSAGE);
        Mockito.verify(this.constraintViolationBuilder).addPropertyNode(FIRST_DATE_PROPERTY_NAME);
        Mockito.verify(this.nodeBuilderCustomizableContext).addConstraintViolation();
    }

    /**
     * Test isValid when the property named firstDatePropertyName does not exist in the target object.
     */
    @Test
    public void testIsValidNoFirstDateProperty() {
        ValidDateRangeValidator validator = new ValidDateRangeValidator();
        ReflectionTestUtils.setField(validator, "firstDatePropertyName", "beginDate");
        ReflectionTestUtils.setField(validator, "secondDatePropertyName", SECOND_DATE_PROPERTY_NAME);
        ReflectionTestUtils.setField(validator, "message", MESSAGE);
        TargetObject object = new TargetObject(Optional.of("2021-02-02"), Optional.of("2021-02-01"));

        Assert.assertTrue(validator.isValid(object, this.context));
        Mockito.verify(this.context, Mockito.never()).disableDefaultConstraintViolation();
        Mockito.verify(this.context, Mockito.never()).buildConstraintViolationWithTemplate(Mockito.any());
        Mockito.verify(this.constraintViolationBuilder, Mockito.never()).addPropertyNode(Mockito.any());
        Mockito.verify(this.nodeBuilderCustomizableContext, Mockito.never()).addConstraintViolation();
    }

    /**
     * Test isValid when the property named secondDatePropertyName does not exist in the target object.
     */
    @Test
    public void testIsValidNoSecondDateProperty() {
        ValidDateRangeValidator validator = new ValidDateRangeValidator();
        ReflectionTestUtils.setField(validator, "firstDatePropertyName", FIRST_DATE_PROPERTY_NAME);
        ReflectionTestUtils.setField(validator, "secondDatePropertyName", "completionDate");
        ReflectionTestUtils.setField(validator, "message", MESSAGE);
        TargetObject object = new TargetObject(Optional.of("2021-02-02"), Optional.of("2021-02-01"));

        Assert.assertTrue(validator.isValid(object, this.context));
        Mockito.verify(this.context, Mockito.never()).disableDefaultConstraintViolation();
        Mockito.verify(this.context, Mockito.never()).buildConstraintViolationWithTemplate(Mockito.any());
        Mockito.verify(this.constraintViolationBuilder, Mockito.never()).addPropertyNode(Mockito.any());
        Mockito.verify(this.nodeBuilderCustomizableContext, Mockito.never()).addConstraintViolation();
    }

    /**
     * Test isValid when the value of the property named firstDatePropertyName cannot be parsed.
     */
    @Test
    public void testIsValidInvalidFirstDateProperty() {
        ValidDateRangeValidator validator = getConfiguredValidator();
        TargetObject object = new TargetObject(Optional.of("2021-13-02"), Optional.of("2021-02-01"));

        Assert.assertTrue(validator.isValid(object, this.context));
        Mockito.verify(this.context, Mockito.never()).disableDefaultConstraintViolation();
        Mockito.verify(this.context, Mockito.never()).buildConstraintViolationWithTemplate(Mockito.any());
        Mockito.verify(this.constraintViolationBuilder, Mockito.never()).addPropertyNode(Mockito.any());
        Mockito.verify(this.nodeBuilderCustomizableContext, Mockito.never()).addConstraintViolation();
    }

    /**
     * Test isValid when the value of the property named secondDatePropertyName cannot be parsed.
     */
    @Test
    public void testIsValidInvalidSecondDateProperty() {
        ValidDateRangeValidator validator = getConfiguredValidator();
        TargetObject object = new TargetObject(Optional.of("2021-03-02"), Optional.empty());

        Assert.assertTrue(validator.isValid(object, this.context));
        Mockito.verify(this.context, Mockito.never()).disableDefaultConstraintViolation();
        Mockito.verify(this.context, Mockito.never()).buildConstraintViolationWithTemplate(Mockito.any());
        Mockito.verify(this.constraintViolationBuilder, Mockito.never()).addPropertyNode(Mockito.any());
        Mockito.verify(this.nodeBuilderCustomizableContext, Mockito.never()).addConstraintViolation();
    }


    /**
     * Test isValid when the the properties are not {@code Optional<String>}.
     */
    @Test
    public void testIsValidWrongType() {
        ValidDateRangeValidator validator = getConfiguredValidator();
        TargetObjectWrongTypes object = new TargetObjectWrongTypes("2021-03-02", "2021-04-02");

        Assert.assertTrue(validator.isValid(object, this.context));
        Mockito.verify(this.context, Mockito.never()).disableDefaultConstraintViolation();
        Mockito.verify(this.context, Mockito.never()).buildConstraintViolationWithTemplate(Mockito.any());
        Mockito.verify(this.constraintViolationBuilder, Mockito.never()).addPropertyNode(Mockito.any());
        Mockito.verify(this.nodeBuilderCustomizableContext, Mockito.never()).addConstraintViolation();
    }

    /**
     * Returns a ValidDateRangeValidator with standard configuration.
     * @return a ValidDateRangeValidator
     */
    private ValidDateRangeValidator getConfiguredValidator() {
        ValidDateRangeValidator validator = new ValidDateRangeValidator();
        ReflectionTestUtils.setField(validator, "firstDatePropertyName", FIRST_DATE_PROPERTY_NAME);
        ReflectionTestUtils.setField(validator, "secondDatePropertyName", SECOND_DATE_PROPERTY_NAME);
        ReflectionTestUtils.setField(validator, "message", MESSAGE);
        return validator;
    }

    /**
     * Inner class used for tests.
     */
    public class TargetObject {
        private final Optional<String> startDate;
        private final Optional<String> endDate;
        public TargetObject(Optional<String> startDate, Optional<String> endDate) {
            this.startDate = startDate;
            this.endDate = endDate;
        }
        public Optional<String> getStartDate() {
            return this.startDate;
        }
        public Optional<String> getEndDate() {
            return this.endDate;
        }
    }

    /**
     * Inner class used for tests.
     */
    public class TargetObjectWrongTypes {
        private final String startDate;
        private final String endDate;
        public TargetObjectWrongTypes(String startDate, String endDate) {
            this.startDate = startDate;
            this.endDate = endDate;
        }
        public String getStartDate() {
            return this.startDate;
        }
        public String getEndDate() {
            return this.endDate;
        }
    }
}
