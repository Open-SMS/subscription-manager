package uk.co.zodiac2000.subscriptionmanager.configuration;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

/**
 * Configures a fixed Clock bean for use in tests. The clock is set to 2012-06-03T10:15:30Z UTC.
 * See
 * <a href="https://stackoverflow.com/questions/62817126/mock-localdate-nowclock-using-clock-fixed">stackoverflow</a>
 * for more examples of overriding the Clock bean.
 */
@TestConfiguration
public class TestClockConfiguration {

    /**
     * Clock used integration tests when this class is loaded in the Spring context.
     */
    public static final Clock TEST_CLOCK = Clock.fixed(Instant.parse("2012-06-03T10:15:30Z"), ZoneId.of("UTC"));

    /**
     * Defines a bean that returns a fixed Clock object set to 2012-06-03T10:15:30Z UTC.
     * @return a fixed Clock object
     */
    @Bean
    @Primary
    public Clock fixedClock() {
        return TEST_CLOCK;
    }

}
