package uk.co.zodiac2000.subscriptionmanager.constraint.validator;

import java.util.Optional;
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
import uk.co.zodiac2000.subscriptionmanager.constraint.DoesNotExist;

/**
 * Unit tests for DoesNotExistValidator.
 */
@Listeners(MockitoTestNGListener.class)
public class DoesNotExistValidatorTest {

    private static final String EXPRESSION = "true";
    private static final String PROPERTY_NAME = "name";
    private static final String IDENTIFIER_PROPERTY_NAME = "id";
    private static final String MESSAGE = "message";
    private static final Long ID = 42L;
    private static final String NAME = "Robert";

    @Mock
    private BeanFactory beanFactory;

    @Mock(lenient = true)
    private Expression verificationExpression;

    @Mock
    private StandardEvaluationContext evaluationContext;

    @Mock(lenient = true)
    private ConstraintValidatorContext.ConstraintViolationBuilder constraintViolationBuilder;

    @Mock(lenient = true)
    private ConstraintValidatorContext.ConstraintViolationBuilder.NodeBuilderCustomizableContext nodeBuilderCustomizableContext;

    @Mock(lenient = true)
    private DoesNotExist constraintAnnotation;

    @Mock(lenient = true)
    private ConstraintValidatorContext validatorContext;

    private final TargetObjectNoId nullReturnTargetObject = new TargetObjectNoId(NAME);
    private final TargetObjectNoId notFoundTargetObject = new TargetObjectNoId(NAME);
    private final TargetObject targetObjectNullId = new TargetObject(null, NAME);
    private final TargetObjectNoId targetObjectNoId = new TargetObjectNoId(NAME);
    private final TargetObject targetObjectIdNotEquals = new TargetObject(87L, NAME);
    private final TargetObject targetObjectIdEquals = new TargetObject(ID, NAME);

    @InjectMocks
    private DoesNotExistValidator validator;

    @BeforeMethod
    public void setUpVerificationExpression() {
        Mockito.when(this.verificationExpression.getValue(Mockito.eq(this.evaluationContext),
                Mockito.eq(this.nullReturnTargetObject),
                Mockito.eq(Optional.class)))
                .thenReturn(null);

        Mockito.when(this.verificationExpression.getValue(Mockito.eq(this.evaluationContext),
                Mockito.eq(this.notFoundTargetObject),
                Mockito.eq(Optional.class)))
                .thenReturn(Optional.empty());

        Mockito.when(this.verificationExpression.getValue(Mockito.eq(this.evaluationContext),
                Mockito.eq(this.targetObjectNullId),
                Mockito.eq(Optional.class)))
                .thenReturn(Optional.of(ID));

        Mockito.when(this.verificationExpression.getValue(Mockito.eq(this.evaluationContext),
                Mockito.eq(this.targetObjectNoId),
                Mockito.eq(Optional.class)))
                .thenReturn(Optional.of(ID));

        Mockito.when(this.verificationExpression.getValue(Mockito.eq(this.evaluationContext),
                Mockito.eq(this.targetObjectIdNotEquals),
                Mockito.eq(Optional.class)))
                .thenReturn(Optional.of(ID));

        Mockito.when(this.verificationExpression.getValue(Mockito.eq(this.evaluationContext),
                Mockito.eq(this.targetObjectIdEquals),
                Mockito.eq(Optional.class)))
                .thenReturn(Optional.of(ID));
    }

    @BeforeMethod
    public void setUpValidatorContext() {
        Mockito.when(this.validatorContext.buildConstraintViolationWithTemplate(Mockito.any()))
                .thenReturn(this.constraintViolationBuilder);
        Mockito.when(this.constraintViolationBuilder.addPropertyNode(Mockito.any()))
                .thenReturn(this.nodeBuilderCustomizableContext);
    }

    @BeforeMethod
    public void setUpConstraintAnnotation() {
        Mockito.when(this.constraintAnnotation.expression()).thenReturn(EXPRESSION);
        Mockito.when(this.constraintAnnotation.propertyName()).thenReturn(PROPERTY_NAME);
        Mockito.when(this.constraintAnnotation.identfierPropertyName()).thenReturn(IDENTIFIER_PROPERTY_NAME);
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
        Assert.assertEquals(ReflectionTestUtils.getField(this.validator, "propertyName"), PROPERTY_NAME);
        Assert.assertEquals(ReflectionTestUtils.getField(this.validator, "identfierPropertyName"), IDENTIFIER_PROPERTY_NAME);
        Assert.assertEquals(ReflectionTestUtils.getField(this.validator, "message"), MESSAGE);
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
        this.validator.isValid(this.nullReturnTargetObject, this.validatorContext);
    }

    /**
     * Test isValid when the verificationExpression returns an empty optional because an object was not found by the
     * SPeL expression.
     */
    @Test
    public void testIsValidNotFound() {
        Assert.assertTrue(this.validator.isValid(this.notFoundTargetObject, this.validatorContext));
        Mockito.verify(this.validatorContext, Mockito.never()).disableDefaultConstraintViolation();
        Mockito.verify(this.validatorContext, Mockito.never()).buildConstraintViolationWithTemplate(Mockito.any());
        Mockito.verify(this.constraintViolationBuilder, Mockito.never()).addPropertyNode(Mockito.any());
        Mockito.verify(this.nodeBuilderCustomizableContext, Mockito.never()).addConstraintViolation();
    }
    
    /**
     * Test isValid when the verificationExpression returns a optional that is present because an object was found by the
     * SPeL expression. The targetObject id property is null so this is a create operation and isValid should return false
     * because the value already exists.
     */
    @Test
    public void testIsValidFoundNullId() {
        ReflectionTestUtils.setField(this.validator, "identfierPropertyName", IDENTIFIER_PROPERTY_NAME);
        ReflectionTestUtils.setField(this.validator, "message", MESSAGE);
        ReflectionTestUtils.setField(this.validator, "propertyName", PROPERTY_NAME);

        Assert.assertFalse(this.validator.isValid(this.targetObjectNullId, this.validatorContext));
        Mockito.verify(this.validatorContext).disableDefaultConstraintViolation();
        Mockito.verify(this.validatorContext).buildConstraintViolationWithTemplate(MESSAGE);
        Mockito.verify(this.constraintViolationBuilder).addPropertyNode(PROPERTY_NAME);
        Mockito.verify(this.nodeBuilderCustomizableContext).addConstraintViolation();
    }
    
    /**
     * Test isValid when the verificationExpression returns a optional that is present because an object was found by the
     * SPeL expression. The targetObject id property does not exist so this is a create operation and isValid should return false
     * because the value already exists.
     */
    @Test
    public void testIsValidFoundNoId() {
        ReflectionTestUtils.setField(this.validator, "identfierPropertyName", IDENTIFIER_PROPERTY_NAME);
        ReflectionTestUtils.setField(this.validator, "message", MESSAGE);
        ReflectionTestUtils.setField(this.validator, "propertyName", PROPERTY_NAME);

        Assert.assertFalse(this.validator.isValid(this.targetObjectNoId, this.validatorContext));
        Mockito.verify(this.validatorContext).disableDefaultConstraintViolation();
        Mockito.verify(this.validatorContext).buildConstraintViolationWithTemplate(MESSAGE);
        Mockito.verify(this.constraintViolationBuilder).addPropertyNode(PROPERTY_NAME);
        Mockito.verify(this.nodeBuilderCustomizableContext).addConstraintViolation();
    }
    
    /**
     * Test isValid when the verificationExpression returns a optional that is present because an object was found by the
     * SPeL expression. The targetObject id property is also set so this is an update operation. Because the id of the object
     * being updated differs from the id of the object found by the SPeL expression the value being set already exists and
     * isValid should return false.
     */
    @Test
    public void testIsValidFoundIdNotEqual() {
        ReflectionTestUtils.setField(this.validator, "identfierPropertyName", IDENTIFIER_PROPERTY_NAME);
        ReflectionTestUtils.setField(this.validator, "message", MESSAGE);
        ReflectionTestUtils.setField(this.validator, "propertyName", PROPERTY_NAME);

        Assert.assertFalse(this.validator.isValid(this.targetObjectIdNotEquals, this.validatorContext));
        Mockito.verify(this.validatorContext).disableDefaultConstraintViolation();
        Mockito.verify(this.validatorContext).buildConstraintViolationWithTemplate(MESSAGE);
        Mockito.verify(this.constraintViolationBuilder).addPropertyNode(PROPERTY_NAME);
        Mockito.verify(this.nodeBuilderCustomizableContext).addConstraintViolation();
    }
    
    /**
     * Test isValid when the verificationExpression returns a optional that is present because an object was found by the
     * SPeL expression. The targetObject id property is also set so this is an update operation. Because the id of the object
     * being updated equals the id of the object found by the SPeL expression the value being set already exists but is a
     * property of the object being updated so isValid should return true.
     */
    @Test
    public void testIsValidFoundIdEqual() {
        ReflectionTestUtils.setField(this.validator, "identfierPropertyName", IDENTIFIER_PROPERTY_NAME);

        Assert.assertTrue(this.validator.isValid(this.targetObjectIdEquals, this.validatorContext));
        Mockito.verify(this.validatorContext, Mockito.never()).disableDefaultConstraintViolation();
        Mockito.verify(this.validatorContext, Mockito.never()).buildConstraintViolationWithTemplate(Mockito.any());
        Mockito.verify(this.constraintViolationBuilder, Mockito.never()).addPropertyNode(Mockito.any());
        Mockito.verify(this.nodeBuilderCustomizableContext, Mockito.never()).addConstraintViolation();
    }
    

    /**
     * Inner class used for tests.
     */
    public class TargetObject {
        private final String name;
        private final Long id;
        public TargetObject(Long id, String name) {
            this.id = id;
            this.name = name;
        }
        public String getName() {
            return this.name;
        }
        public Long getId() {
            return this.id;
        }
    }

    /**
     * Inner class used for tests.
     */
    public class TargetObjectNoId {
        private final String name;
        public TargetObjectNoId(String name) {
            this.name = name;
        }
        public String getName() {
            return this.name;
        }
    }
}
