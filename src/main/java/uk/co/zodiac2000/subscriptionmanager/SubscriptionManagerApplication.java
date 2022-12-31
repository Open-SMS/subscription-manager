package uk.co.zodiac2000.subscriptionmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main class for Subscription Manager application.
 */
@SpringBootApplication
public class SubscriptionManagerApplication {

    /**
     * Hide constructor to prevent instantiation.
     */
    protected SubscriptionManagerApplication() { }

    /**
     * Main application method.
     * @param args command arguments
     */
    public static void main(final String[] args) {
        SpringApplication.run(SubscriptionManagerApplication.class, args);
    }

}
