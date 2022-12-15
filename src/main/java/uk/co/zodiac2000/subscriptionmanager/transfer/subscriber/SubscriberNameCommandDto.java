package uk.co.zodiac2000.subscriptionmanager.transfer.subscriber;

/**
 * Command DTO representing a change to a subscriber name.
 */
public class SubscriberNameCommandDto {

    private String subscriberName;

    /**
     * Zero-arg constructor to allow ObjectMapper to create this class.
     */
    public SubscriberNameCommandDto() { }

    /**
     * Constructs a new SubscriberNameCommandDto using the supplied arguments.
     * @param subscriberName the subscriber name
     */
    public SubscriberNameCommandDto(String subscriberName) {
        this.subscriberName = subscriberName;
    }

    /**
     * @return the subscriber name
     */
    public String getSubscriberName() {
        return this.subscriberName;
    }
}
