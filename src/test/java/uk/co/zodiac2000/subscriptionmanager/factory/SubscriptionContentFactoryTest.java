package uk.co.zodiac2000.subscriptionmanager.factory;

import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import org.testng.annotations.Test;
import uk.co.zodiac2000.subscriptionmanager.domain.subscriptioncontent.SubscriptionContent;
import uk.co.zodiac2000.subscriptionmanager.transfer.subscriptioncontent.ContentIdentifierCommandDto;
import uk.co.zodiac2000.subscriptionmanager.transfer.subscriptioncontent.NewSubscriptionContentCommandDto;

/**
 * Unit tests for SubscriptionContentFactory.
 */
public class SubscriptionContentFactoryTest {

    private static final String CONTENT_DESCRIPTION = "Index Islamicus";
    private static final List<ContentIdentifierCommandDto> CONTENT_IDENTIFIERS = List.of(
            new ContentIdentifierCommandDto("Biblography"),
            new ContentIdentifierCommandDto("GeneralIndex")
    );
    private static final String SUBSCRIPTION_RESOURCE_ID = "42";
    private static final NewSubscriptionContentCommandDto COMMAND_DTO = new NewSubscriptionContentCommandDto(
            CONTENT_DESCRIPTION, CONTENT_IDENTIFIERS, SUBSCRIPTION_RESOURCE_ID);

    private final SubscriptionContentFactory factory = new SubscriptionContentFactory();

    /**
     * Test newSubscriptionContentCommandDtoToSubscriptionContent.
     */
    @Test
    public void testNewSubscriptionContentCommandDtoToSubscriptionContent() {
        SubscriptionContent subscriptionContent
                = this.factory.newSubscriptionContentCommandDtoToSubscriptionContent(COMMAND_DTO);

        assertThat(subscriptionContent, allOf(
                hasProperty("contentDescription", is(CONTENT_DESCRIPTION)),
                hasProperty("contentIdentifiers", containsInAnyOrder(
                        hasProperty("contentIdentifier", is("Biblography")),
                        hasProperty("contentIdentifier", is("GeneralIndex"))
                )),
                hasProperty("subscriptionResourceId", is(Long.valueOf(SUBSCRIPTION_RESOURCE_ID)))
        ));
    }
}
