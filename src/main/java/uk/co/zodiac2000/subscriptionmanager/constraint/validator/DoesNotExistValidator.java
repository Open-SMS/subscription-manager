package uk.co.zodiac2000.subscriptionmanager.constraint.validator;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ValidationException;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import uk.co.zodiac2000.subscriptionmanager.constraint.DoesNotExist;

/**
 * Validator used by {@link uk.co.zodiac2000.subscriptionmanager.constraint.DoesNotExist} constraint.
 */
public class DoesNotExistValidator implements ConstraintValidator<DoesNotExist, Object>, BeanFactoryAware {

    private BeanFactory beanFactory;

    private Expression verificationExpression;

    private StandardEvaluationContext evaluationContext;

    private String propertyName;

    private String message;

    private String identfierPropertyName;

    /** 
     * Initializes the validator.
     * @param constraintAnnotation the constraint annotation
     */
    @Override
    public void initialize(DoesNotExist constraintAnnotation) {
        ExpressionParser parser = new SpelExpressionParser();
        this.evaluationContext = new StandardEvaluationContext();
        this.evaluationContext.setBeanResolver(new BeanFactoryResolver(this.beanFactory));
        this.verificationExpression = parser.parseExpression(constraintAnnotation.expression());

        this.propertyName = constraintAnnotation.propertyName();
        this.identfierPropertyName = constraintAnnotation.identfierPropertyName();
        this.message = constraintAnnotation.message();
    }

    /**
     * Returns true if the SpEL expression returns an empty optional, or the operation is an update and the identified
     * object is the object being updated. See {@link uk.co.zodiac2000.subscriptionmanager.constraint.DoesNotExist}
     * for details. If {@code value} is not valid then problem is recorded in {@code context}.
     * @param value the object being validated
     * @param context the context in which the object is validated
     * @return false if value does not pass the constraint
     */
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        Optional<Object> id = this.verificationExpression.getValue(this.evaluationContext, value, Optional.class);
        if (id == null) {
            throw new ValidationException("Unexpected null return from expression evaluation");
        }

        boolean isValid = id.isEmpty()
                || getPropertyValue(value, this.identfierPropertyName)
                        .map(v -> id.get().equals(v))
                        .orElse(false);

        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(this.message)
                    .addPropertyNode(this.propertyName)
                    .addConstraintViolation();
        }

        return isValid;
    }

    /**
     * Returns an {@code Optional} containing the value of the the property identified by {@code propertyName}
     * in {@code targetObject}, or an empty {@code Optional} if the property value is null, the property does not
     * exist, or the property cannot be accessed.
     * @param targetObject the object to obtain the property value from
     * @param propertyName the property name
     * @return an optional containing the property value
     */
    private Optional<Object> getPropertyValue(Object targetObject, String propertyName) {
        try {
            return Optional.ofNullable(PropertyUtils.getProperty(targetObject, propertyName));
        } catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
            return Optional.empty();
        }
    }

    /**
     * Callback that supplies the owning factory to a bean instance.
     * @param beanFactory owning BeanFactory
     */
    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }
}
