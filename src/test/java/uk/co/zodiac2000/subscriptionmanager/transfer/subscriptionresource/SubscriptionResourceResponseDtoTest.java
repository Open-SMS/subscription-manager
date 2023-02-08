package uk.co.zodiac2000.subscriptionmanager.transfer.subscriptionresource;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Unit tests for SubscriptionResourceResponseDto.
 */
public class SubscriptionResourceResponseDtoTest {

    private static final long ID = 42L;
    private static final String RESOURCE_URI = "https://example.com";
    private static final String RESOURCE_DESCRIPTION = "Example";

    /**
     * Test constructor and accessors.
     */
    @Test
    public void testConstructor() {
        SubscriptionResourceResponseDto dto
                = new SubscriptionResourceResponseDto(ID, RESOURCE_URI, RESOURCE_DESCRIPTION);

        Assert.assertEquals(dto.getId(), ID);
        Assert.assertEquals(dto.getResourceUri(), RESOURCE_URI);
        Assert.assertEquals(dto.getResourceDescription(),  RESOURCE_DESCRIPTION);
    }
}
