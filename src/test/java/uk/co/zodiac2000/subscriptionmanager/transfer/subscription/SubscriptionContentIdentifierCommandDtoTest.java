package uk.co.zodiac2000.subscriptionmanager.transfer.subscription;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Unit tests for SubscriptionContentIdentifierCommandDto.
 */
public class SubscriptionContentIdentifierCommandDtoTest {

    private final static String CONTENT_IDENTIFIER = "CONTENT";

    /**
     * Test constructor and accessors.
     */
    @Test
    public void testAccessors() {
        SubscriptionContentIdentifierCommandDto commandDto = new SubscriptionContentIdentifierCommandDto(CONTENT_IDENTIFIER);

        Assert.assertEquals(commandDto.getContentIdentifier(), CONTENT_IDENTIFIER);
    }

    /**
     * Test zero-arg constructor.
     */
    @Test
    public void testZeroArgConstructor() {
        SubscriptionContentIdentifierCommandDto commandDto = new SubscriptionContentIdentifierCommandDto();

        Assert.assertNotNull(commandDto);
    }
}
