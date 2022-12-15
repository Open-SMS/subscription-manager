package uk.co.zodiac2000.subscriptionmanager.transfer.subscriber;

import javax.validation.constraints.NotEmpty;

/**
 * Command DTO representing a request to create a new Subscriber aggregate root.
 */
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
