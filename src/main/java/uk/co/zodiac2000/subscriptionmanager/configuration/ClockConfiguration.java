package uk.co.zodiac2000.subscriptionmanager.configuration;

import java.time.Clock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Clock bean.
 */
@Configuration
public class ClockConfiguration {

    /**
     * Defines a bean that returns a Clock object set to the current system time expressed in UTC. This
     * should be used with any methods used to produce the current date or time. When writing integration
     * tests an alternate implementation of this bean should be used that returns a fixed instant.
     * @return a Clock object set to UTC
     */
    @Bean
    public Clock systemClock() {
        return Clock.systemUTC();
    }
}
