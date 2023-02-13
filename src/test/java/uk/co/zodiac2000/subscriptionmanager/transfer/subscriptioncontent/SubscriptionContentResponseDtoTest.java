package uk.co.zodiac2000.subscriptionmanager.transfer.subscriptioncontent;

import java.util.List;
import org.testng.Assert;
import org.testng.annotations.Test;
import uk.co.zodiac2000.subscriptionmanager.transfer.subscriptionresource.SubscriptionResourceResponseDto;

/**
 * Unit tests for SubscriptionContentResponseDto.
 */
public class SubscriptionContentResponseDtoTest {

    private static final long ID = 42L;
    private static final String SUBSCRIPTION_CONTENT_DESCRIPTION = "Example Content";
    private static final List<String> CONTENT_IDENTIFIERS = List.of("EXAMPLE-ONE", "EXAMPLE-TWO");
    private static final SubscriptionResourceResponseDto SUBSCRIPTION_RESOURCE = new SubscriptionResourceResponseDto(
            89L, "https://example.com", "Example");

    /**
     * Test constructor and accessors.
     */
    @Test
    public void testConstructor() {
        SubscriptionContentResponseDto responseDto = new SubscriptionContentResponseDto(ID,
                SUBSCRIPTION_CONTENT_DESCRIPTION, CONTENT_IDENTIFIERS, SUBSCRIPTION_RESOURCE);

        Assert.assertEquals(responseDto.getId(), ID);
        Assert.assertEquals(responseDto.getContentDescription(), SUBSCRIPTION_CONTENT_DESCRIPTION);
        Assert.assertEquals(responseDto.getContentIdentifiers(), CONTENT_IDENTIFIERS);
        Assert.assertSame(responseDto.getSubscriptionResource(), SUBSCRIPTION_RESOURCE);
    }
}
