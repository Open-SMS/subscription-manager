package uk.co.zodiac2000.subscriptionmanager.transfer.subscriber;

import javax.validation.constraints.NotEmpty;
import uk.co.zodiac2000.subscriptionmanager.constraint.DoesNotExist;

/**
 * Command DTO representing a request to create a new Subscriber aggregate root.
 */
@DoesNotExist(expression = "@subscriberService.getSubscriberIdBySubscriberName(#this.subscriberName)",
        propertyName = "subscriberName")
public class NewSubscriberCommandDto {

    @NotEmpty
    private String subscriberName;

    /**
     * Zero-arg constructor to allow ObjectMapper to create this class.
     */
    public NewSubscriberCommandDto() { }

    /**
     * Constructs a new NewSubscriberCommandDto using the supplied arguments.
     * @param subscriberName this subscriber's name
     */
    public NewSubscriberCommandDto(String subscriberName) {
        this.subscriberName = subscriberName;
    }

    /**
     * @return this subscriber's name
     */
    public String getSubscriberName() {
        return this.subscriberName;
    }
}
