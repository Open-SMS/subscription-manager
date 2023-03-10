package uk.co.zodiac2000.subscriptionmanager.domain.subscriptioncontent;

import java.util.List;
import java.util.Set;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import uk.co.zodiac2000.subscriptionmanager.transfer.subscriptioncontent.ContentIdentifierCommandDto;
import uk.co.zodiac2000.subscriptionmanager.transfer.subscriptioncontent.UpdateSubscriptionContentCommandDto;

/**
 * Unit tests for SubscriptionContent.
 */
public class SubscriptionContentTest {

    private static final String SUBSCRIPTION_CONTENT_DESCRIPTION = "Example Content";
    private static final String CONTENT_IDENTIFIER = "CONTENT";
    private static final Set<ContentIdentifier> CONTENT_IDENTIFIERS = Set.of(
            new ContentIdentifier(CONTENT_IDENTIFIER)
    );
    private static final Long SUBSCRIPTION_RESOURCE_ID = 42L;

    /**
     * Test constructor and accessors.
     */
    @Test
    public void testConstructor() {
        SubscriptionContent subscriptionContent = new SubscriptionContent(SUBSCRIPTION_CONTENT_DESCRIPTION,
                CONTENT_IDENTIFIERS, SUBSCRIPTION_RESOURCE_ID);

        Assert.assertNull(subscriptionContent.getId());
        Assert.assertEquals(subscriptionContent.getContentDescription(), SUBSCRIPTION_CONTENT_DESCRIPTION);
        Assert.assertEquals(subscriptionContent.getContentIdentifiers(), CONTENT_IDENTIFIERS);
        Assert.assertEquals(subscriptionContent.getSubscriptionResourceId(), SUBSCRIPTION_RESOURCE_ID);
    }

    /**
     * Test zero-args constructor.
     */
    @Test
    public void testZeroArgsConstructor() {
        SubscriptionContent subscriptionContent = new SubscriptionContent();

        Assert.assertNotNull(subscriptionContent);
    }

    /**
     * Test constructor when contentDescription is null.
     */
    @Test(expectedExceptions = {NullPointerException.class})
    public void testConstructorContentDescriptionNull() {
        SubscriptionContent subscriptionContent = new SubscriptionContent(null,
                CONTENT_IDENTIFIERS, SUBSCRIPTION_RESOURCE_ID);
    }

    /**
     * Test constructor when contentIdentifiers are null.
     */
    @Test(expectedExceptions = {NullPointerException.class})
    public void testConstructorContentIdentifiersNull() {
        SubscriptionContent subscriptionContent = new SubscriptionContent(SUBSCRIPTION_CONTENT_DESCRIPTION,
                null, SUBSCRIPTION_RESOURCE_ID);
    }

    /**
     * Test constructor when subscriptionResourceId is null.
     */
    @Test(expectedExceptions = {NullPointerException.class})
    public void testConstructorSubscriptionResourceIdNull() {
        SubscriptionContent subscriptionContent = new SubscriptionContent(SUBSCRIPTION_CONTENT_DESCRIPTION,
                CONTENT_IDENTIFIERS, null);
    }

    /**
     * Test updateSubscriptionContent.
    */
    @Test
    public void testUpdateSubscriptionContent() {
        UpdateSubscriptionContentCommandDto commandDto = new UpdateSubscriptionContentCommandDto(
                87L, SUBSCRIPTION_CONTENT_DESCRIPTION,
                List.of(new ContentIdentifierCommandDto(CONTENT_IDENTIFIER)),
                SUBSCRIPTION_RESOURCE_ID.toString());
        SubscriptionContent subscriptionContent = new SubscriptionContent();
        subscriptionContent.updateSubscriptionContent(commandDto);

        Assert.assertEquals(subscriptionContent.getContentDescription(), SUBSCRIPTION_CONTENT_DESCRIPTION);
        assertThat(subscriptionContent, allOf(
                hasProperty("contentDescription", is(SUBSCRIPTION_CONTENT_DESCRIPTION)),
                hasProperty("contentIdentifiers", containsInAnyOrder(
                        hasProperty("contentIdentifier", is(CONTENT_IDENTIFIER))
                )),
                hasProperty("subscriptionResourceId", is(SUBSCRIPTION_RESOURCE_ID))
        ));
    }
}
