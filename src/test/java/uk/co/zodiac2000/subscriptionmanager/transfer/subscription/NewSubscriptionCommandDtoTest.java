package uk.co.zodiac2000.subscriptionmanager.transfer.subscription;

import java.util.Optional;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Unit tests for NewSubscriptionCommandDto.
 */
public class NewSubscriptionCommandDtoTest {

    private static final Optional<String> START_DATE = Optional.of("2021-02-03");
    private static final Optional<String> END_DATE = Optional.of("2022-02-03");
    private static final String CONTENT_IDENTIFIER = "CONTENT";
    private static final String SUBSCRIBER_ID = "142";

    /**
     * Test constructor and accessors.
     */
    @Test
    public void testAccessors() {
        NewSubscriptionCommandDto commandDto = new NewSubscriptionCommandDto(START_DATE, END_DATE, CONTENT_IDENTIFIER, SUBSCRIBER_ID);

        Assert.assertEquals(commandDto.getStartDate(), START_DATE);
        Assert.assertEquals(commandDto.getEndDate(), END_DATE);
        Assert.assertEquals(commandDto.getContentIdentifier(), CONTENT_IDENTIFIER);
        Assert.assertEquals(commandDto.getSubscriberId(), SUBSCRIBER_ID);
    }

    /**
     * Test zero-arg constructor.
     */
    @Test
    public void testZeroArgConstructor() {
        NewSubscriptionCommandDto commandDto = new NewSubscriptionCommandDto();

        Assert.assertNotNull(commandDto);
    }
}
