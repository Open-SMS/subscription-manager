package uk.co.zodiac2000.subscriptionmanager.transfer.subscriptioncontent;

import java.util.List;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Unit tests for UpdateSubscriptionContentCommandDto.
 */
public class UpdateSubscriptionContentCommandDtoTest {

    private static final long ID = 87L;
    private static final String SUBSCRIPTION_CONTENT_DESCRIPTION = "Example Content";
    private static final List<ContentIdentifierCommandDto> CONTENT_IDENTIFIERS = List.of(
            new ContentIdentifierCommandDto("CONTENT-ONE")
    );
    private static final String SUBSCRIPTION_RESOURCE_ID = "42";

    /**
     * Test constructor and accessors.
     */
    @Test
    public void testConstructor() {
        UpdateSubscriptionContentCommandDto commandDto = new UpdateSubscriptionContentCommandDto(ID,
                SUBSCRIPTION_CONTENT_DESCRIPTION, CONTENT_IDENTIFIERS, SUBSCRIPTION_RESOURCE_ID);

        Assert.assertEquals(commandDto.getId(), ID);
        Assert.assertEquals(commandDto.getContentDescription(), SUBSCRIPTION_CONTENT_DESCRIPTION);
        Assert.assertEquals(commandDto.getContentIdentifiers(), CONTENT_IDENTIFIERS);
        Assert.assertEquals(commandDto.getSubscriptionResourceId(), SUBSCRIPTION_RESOURCE_ID);
    }

    /**
     * Test zero-arg constructor.
     */
    @Test
    public void testZeroArgConstructor() {
        UpdateSubscriptionContentCommandDto commandDto = new UpdateSubscriptionContentCommandDto();

        Assert.assertNotNull(commandDto);
    }

    /**
     * Test setId.
     */
    @Test
    public void testSetId() {
        UpdateSubscriptionContentCommandDto commandDto = new UpdateSubscriptionContentCommandDto();
        commandDto.setId(ID);

        Assert.assertEquals(commandDto.getId(), ID);
    }
}
