package uk.co.zodiac2000.subscriptionmanager.constraint.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import uk.co.zodiac2000.subscriptionmanager.constraint.Exists;

/**
 * Validator used by {@link uk.co.zodiac2000.subscriptionmanager.constraint.Exists} constraint.
 */
public class ExistsValidator implements ConstraintValidator<Exists, Object>, BeanFactoryAware {

    private static final Logger LOG = LoggerFactory.getLogger(DoesNotExistValidator.class);

    private BeanFactory beanFactory;

    private Expression verificationExpression;

    private StandardEvaluationContext evaluationContext;


    /**
     * Initializes the validator.
     * @param constraintAnnotation the constraint annotation
     */
    @Override
    public void initialize(final Exists constraintAnnotation) {
        ExpressionParser parser = new SpelExpressionParser();
        this.evaluationContext = new StandardEvaluationContext();
        this.evaluationContext.setBeanResolver(new BeanFactoryResolver(this.beanFactory));
        this.verificationExpression = parser.parseExpression(constraintAnnotation.expression());
    }

    /**
     * Returns the result of evaluation of the SPeL expression if true or false. If the SPeL expression returns
     * null then a ValidationException is thrown.
     * @param value the value being verified
     * @param context the validator context
     * @return true if valid
     */
    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context) {
        Boolean isPresent = this.verificationExpression.getValue(this.evaluationContext, value, Boolean.class);
        if (isPresent == null) {
            throw new ValidationException("Unexpected null value returned by expression evaluation");
        }
        return isPresent;
    }

    /**
     * Callback that supplies the owning factory to a bean instance.
     * @param beanFactory owning BeanFactory
     */
    @Override
    public void setBeanFactory(final BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }
}
