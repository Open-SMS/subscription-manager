package uk.co.zodiac2000.subscriptionmanager.transfer.subscription;

import java.time.LocalDate;
import java.util.Optional;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Unit tests for SubscriptionResponseDto.
 */
public class SubscriptionResponseDtoTest {

    private static final Long ID = 42L;
    private static final Optional<LocalDate> START_DATE = Optional.of(LocalDate.parse("2021-01-01"));
    private static final Optional<LocalDate> END_DATE = Optional.of(LocalDate.parse("2022-01-01"));
    private static final boolean TERMINATED = false;
    private static final String CONTENT_IDENTIFIER = "CONTENT";
    private static final Long SUBSCRIBER_ID = 87L;
    private static final boolean ACTIVE = true;

    /**
     * Test constructor and accessors.
     */
    @Test
    public void testAccessors() {
        SubscriptionResponseDto responseDto = new SubscriptionResponseDto(ID, START_DATE, END_DATE, TERMINATED, CONTENT_IDENTIFIER,
                SUBSCRIBER_ID, ACTIVE);

        Assert.assertEquals(responseDto.getId(), ID);
        Assert.assertEquals(responseDto.getStartDate(), START_DATE);
        Assert.assertEquals(responseDto.getEndDate(), END_DATE);
        Assert.assertFalse(responseDto.isTerminated());
        Assert.assertEquals(responseDto.getContentIdentifier(), CONTENT_IDENTIFIER);
        Assert.assertEquals(responseDto.getSubscriberId(), SUBSCRIBER_ID);
        Assert.assertTrue(responseDto.isActive());
    }
}
