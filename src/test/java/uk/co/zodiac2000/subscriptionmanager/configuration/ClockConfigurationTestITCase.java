package uk.co.zodiac2000.subscriptionmanager.configuration;

import java.time.Clock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test wiring the Clock bean representing the system time.
 */
@SpringBootTest
@TestPropertySource(locations = "classpath:application-integration.properties")
public class ClockConfigurationTestITCase extends AbstractTestNGSpringContextTests {

    @Autowired
    private Clock systemClock;

    /**
     * Check the Clock bean exists.
     */
    @Test
    public void testClock() {
        Assert.assertNotNull(this.systemClock);
    }
}
