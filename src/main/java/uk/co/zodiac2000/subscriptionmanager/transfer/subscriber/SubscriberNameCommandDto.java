package uk.co.zodiac2000.subscriptionmanager.transfer.subscriber;

import javax.validation.constraints.NotEmpty;
import uk.co.zodiac2000.subscriptionmanager.constraint.DoesNotExist;

/**
 * Command DTO representing a change to a subscriber name.
 */
@DoesNotExist(expression = "@subscriberService.getSubscriberIdBySubscriberName(#this.subscriberName)",
        propertyName = "subscriberName")
public class SubscriberNameCommandDto {

    private long id;

    @NotEmpty
    private String subscriberName;

    /**
     * Zero-arg constructor to allow ObjectMapper to create this class.
     */
    public SubscriberNameCommandDto() { }

    /**
     * Constructs a new SubscriberNameCommandDto using the supplied arguments.
     * @param id the subscriber identifier
     * @param subscriberName the subscriber name
     */
    public SubscriberNameCommandDto(final long id, final String subscriberName) {
        this.id = id;
        this.subscriberName = subscriberName;
    }

    /**
     * @param id the subscriber identifier
     */
    public void setId(final long id) {
        this.id = id;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @return the subscriber name
     */
    public String getSubscriberName() {
        return this.subscriberName;
    }
}
