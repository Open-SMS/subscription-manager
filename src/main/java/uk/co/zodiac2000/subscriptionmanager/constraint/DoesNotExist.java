package uk.co.zodiac2000.subscriptionmanager.constraint;

import java.lang.annotation.Documented;
import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import uk.co.zodiac2000.subscriptionmanager.constraint.validator.DoesNotExistValidator;

/**
 * Type constraint that is used to verify whether an object already exists in the system with unique properties matching
 * those used in a DTO being used to create a new object or update an existing object. The check is performed by evaluating
 * a SPeL expression defined by the {@code expression} property. This method should return an {@code Optional} which should
 * contain the identifier of the existing object if found, otherwise an empty optional. Arguments to the method can be
 * properties of the DTO specified by the SPeL expression. If the object exists then the DTO is considered invalid.
 * In the case of an update the identfierPropertyName (which defaults to
 * {@code id}) specifies the identifier property in the DTO. If the value of this property is equal to to value returned
 * by the SPeL expression that the DTO is considered valid because the object that was found is the object being updated.
 * <br><br>
 * For example:
 * <pre>
 * {@code
 * @Service
 * public class SubscriberService {
 *     public Optional<Long> getSubscriberIdBySubscriberName(String subscriberName) {
 *         return this.subscriberRepository.findBySubscriberName(subscriberName).map(Subscriber::getId);
 *     }
 * }
 * 
 * @DoesNotExist(expression = "@subscriberService.getSubscriberIdBySubscriberName(#this.subscriberName)",
 *     propertyName = "subscriberName")
 * public class NewSubscriberCommandDto {
 *     private String subscriberName;
 * }
 * 
 * @DoesNotExist(expression = "@subscriberService.getSubscriberIdBySubscriberName(#this.subscriberName)",
 *     propertyName = "subscriberName", identfierPropertyName = "ident")
 * public class UpdateSubscriberCommandDto {
 *     private long ident;
 *     private String subscriberName;
 * }
 * }
 * </pre>
 */
@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = DoesNotExistValidator.class)
@Documented
public @interface DoesNotExist {

    /**
     * Returns the default message. By default the fully qualified interface name is used.
     * @return default message.
     */
    String message() default "{uk.co.zodiac2000.subscriptionmanager.constraint.DoesNotExist}";

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
     * Returns the SPeL expression used to determine whether an object already exists with the same property values.
     * @return the SpEL expression
     */
    String expression();

    /**
     * Returns the property name that violations of this constraint are reported for.
     * @return the property name that violations of this constraint are reported for
     */
    String propertyName();

    /**
     * Returns the name of the property that identifies the object being updated. Defaults to id.
     * If the value of the property is null, or the property does not exist, then
     * it is assumed the object being verified is used for creation rather than update.
     * @return the name of the property that identifies the object being updated
     */
    String identfierPropertyName() default "id";
}
