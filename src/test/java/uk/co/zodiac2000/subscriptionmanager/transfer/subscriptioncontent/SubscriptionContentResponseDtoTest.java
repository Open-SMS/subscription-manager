package uk.co.zodiac2000.subscriptionmanager.transfer.subscriptioncontent;

import org.testng.Assert;
import org.testng.annotations.Test;
import uk.co.zodiac2000.subscriptionmanager.transfer.subscriptionresource.SubscriptionResourceResponseDto;

/**
 * Unit tests for SubscriptionContentResponseDto.
 */
public class SubscriptionContentResponseDtoTest {

    private static final long ID = 42L;
    private static final String SUBSCRIPTION_CONTENT_DESCRIPTION = "Example Content";
    private static final SubscriptionResourceResponseDto SUBSCRIPTION_RESOURCE = new SubscriptionResourceResponseDto(
            89L, "https://example.com", "Example");

    /**
     * Test constructor and accessors.
     */
    @Test
    public void testConstructor() {
        SubscriptionContentResponseDto responseDto
                = new SubscriptionContentResponseDto(ID, SUBSCRIPTION_CONTENT_DESCRIPTION, SUBSCRIPTION_RESOURCE);

        Assert.assertEquals(responseDto.getId(), ID);
        Assert.assertEquals(responseDto.getContentDescription(), SUBSCRIPTION_CONTENT_DESCRIPTION);
        Assert.assertSame(responseDto.getSubscriptionResource(), SUBSCRIPTION_RESOURCE);
    }
}
