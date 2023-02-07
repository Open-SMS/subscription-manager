package uk.co.zodiac2000.subscriptionmanager.transfer.contentidentifier;

import org.testng.Assert;
import org.testng.annotations.Test;
import uk.co.zodiac2000.subscriptionmanager.transfer.resource.SubscriptionResourceResponseDto;

/**
 * Unit tests for ContentIdentifierResponseDto.
 */
public class ContentIdentifierResponseDtoTest {

    private static final long ID = 42L;
    private static final SubscriptionResourceResponseDto SUBSCRIPTION_RESOURCE = new SubscriptionResourceResponseDto(
            89L, "https://example.com", "Example");

    /**
     * Test constructor and accessors.
     */
    @Test
    public void testConstructor() {
        ContentIdentifierResponseDto responseDto = new ContentIdentifierResponseDto(ID, SUBSCRIPTION_RESOURCE);

        Assert.assertEquals(responseDto.getId(), ID);
        Assert.assertSame(responseDto.getSubscriptionResource(), SUBSCRIPTION_RESOURCE);
    }
}
