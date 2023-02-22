package uk.co.zodiac2000.subscriptionmanager.transfer.subscriptioncontent;

import java.util.List;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Unit tests for NewSubscriptionContentCommandDto.
 */
public class NewSubscriptionContentCommandDtoTest {

    private static final String SUBSCRIPTION_CONTENT_DESCRIPTION = "Example Content";
    private static final List<ContentIdentifierCommandDto> CONTENT_IDENTIFIERS = List.of(
            new ContentIdentifierCommandDto("CONTENT-ONE")
    );

    /**
     * Test constructor and accessors.
     */
    @Test
    public void testConstructor() {
        NewSubscriptionContentCommandDto commandDto = new NewSubscriptionContentCommandDto(
                SUBSCRIPTION_CONTENT_DESCRIPTION, CONTENT_IDENTIFIERS);

        Assert.assertEquals(commandDto.getContentDescription(), SUBSCRIPTION_CONTENT_DESCRIPTION);
        Assert.assertEquals(commandDto.getContentIdentifiers(), CONTENT_IDENTIFIERS);
    }

    /**
     * Test zero-arg constructor.
     */
    @Test
    public void testZeroArgConstructor() {
        NewSubscriptionContentCommandDto commandDto = new NewSubscriptionContentCommandDto();

        Assert.assertNotNull(commandDto);
    }
}
