package uk.co.zodiac2000.subscriptionmanager.constraint;

import java.lang.annotation.Documented;
import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import uk.co.zodiac2000.subscriptionmanager.constraint.validator.ExistsValidator;

/**
 * Field constraint used to check whether an object referenced by the key value in the annotated field exists in
 * the system. This check is typically performed when a command DTO contains a field referencing another object, for
 * example {@code subscriptionContentId} in {@code NewSubscriptionCommandDto}.
 * The check is performed by evaluating a SPeL expression defined by the {@code expression} property. A bean factory
 * provides access to any defined beans.
 * This method should return a boolean indicating whether the referenced object is present or absent. If absent
 * then validation fails.
 * <br><br>
 * For example:
 * <pre>
 * {@code
 * @Service
 * public class SubscriptionContentService {
 *     @Autowired
 *     private SubscriptionContentRepository subscriptionContentRepository;
 *
 *     public boolean isPresent(long id) {
 *         return this.subscriptionContentRepository.existsById(id);
 *     }
 * }
 *
 * public class NewSubscriptionCommandDto {
 *     @Exists(expression = "@subscriptionContentService.isPresent(#this)")
 *     private String subscriptionContentId;
 * }
 * }
 * </pre>
 */
@Target({FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = ExistsValidator.class)
@Documented
public @interface Exists {

    /**
     * Returns the default message. By default the fully qualified interface name is used.
     * @return default message.
     */
    String message() default "{uk.co.zodiac2000.subscriptionmanager.constraint.Exists}";

    /**
     * Returns the default validation groups. There are no default validation groups for this constraint.
     * @return default validation groups.
     */
    Class<?>[] groups() default {};

    /**
     * Returns the payload. No payload is defined by default for this validator.
     * @return the payload
     */
    Class<? extends Payload>[] payload() default {};

    /**
     * Returns the SPeL expression used to determine whether an object exists in the system.
     * @return the SpEL expression
     */
    String expression();
}
