package uk.co.zodiac2000.subscriptionmanager.transfer.subscription;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.testng.Assert;
import org.testng.annotations.Test;
import uk.co.zodiac2000.subscriptionmanager.transfer.subscriptioncontent.SubscriptionContentResponseDto;
import uk.co.zodiac2000.subscriptionmanager.transfer.subscriptionresource.SubscriptionResourceResponseDto;

/**
 * Unit tests for SubscriptionResponseDto.
 */
public class SubscriptionResponseDtoTest {

    private static final Long ID = 42L;
    private static final Optional<LocalDate> START_DATE = Optional.of(LocalDate.parse("2021-01-01"));
    private static final Optional<LocalDate> END_DATE = Optional.of(LocalDate.parse("2022-01-01"));
    private static final boolean TERMINATED = false;
    private static final boolean SUSPENDED = true;
    private static final Long SUBSCRIBER_ID = 87L;
    private static final boolean ACTIVE = false;
    private static final boolean CAN_BE_SUSPENDED = false;
    private static final boolean CAN_BE_TERMINATED = true;
    private static final boolean CAN_BE_UNSUSPENDED = true;
    private static final Long SUBSCRIPTION_CONTENT_ID = 293L;
    private static final String SUBSCRIPTION_CONTENT_DESCRIPTION = "Example Content";
    private static final Long SUBSCRIPTION_RESOURCE_ID = 303L;
    private static final String SUBSCRIPTION_RESOURCE_URI = "https://example.com";
    private static final String SUBSCRIPTION_RESOURCE_DESCRIPTION = "Example";
    private static final SubscriptionContentResponseDto SUBSCRIPTION_CONTENT_RESPONSE_DTO
            = new SubscriptionContentResponseDto(
                    SUBSCRIPTION_CONTENT_ID,
                    SUBSCRIPTION_CONTENT_DESCRIPTION,
                    List.of(),
                    new SubscriptionResourceResponseDto(SUBSCRIPTION_RESOURCE_ID, SUBSCRIPTION_RESOURCE_URI,
                            SUBSCRIPTION_RESOURCE_DESCRIPTION)
            );

    /**
     * Test constructor and accessors.
     */
    @Test
    public void testAccessors() {
        SubscriptionResponseDto responseDto = new SubscriptionResponseDto(ID, START_DATE, END_DATE,
                TERMINATED, SUSPENDED,
                SUBSCRIPTION_CONTENT_RESPONSE_DTO, SUBSCRIBER_ID, ACTIVE,
                CAN_BE_SUSPENDED, CAN_BE_TERMINATED, CAN_BE_UNSUSPENDED
        );

        Assert.assertEquals(responseDto.getId(), ID);
        Assert.assertEquals(responseDto.getStartDate(), START_DATE);
        Assert.assertEquals(responseDto.getEndDate(), END_DATE);
        Assert.assertFalse(responseDto.isTerminated());
        Assert.assertTrue(responseDto.isSuspended());
        Assert.assertSame(responseDto.getSubscriptionContent(), SUBSCRIPTION_CONTENT_RESPONSE_DTO);
        Assert.assertEquals(responseDto.getSubscriberId(), SUBSCRIBER_ID);
        Assert.assertFalse(responseDto.isActive());
        Assert.assertFalse(responseDto.getCanBeSuspended());
        Assert.assertTrue(responseDto.getCanBeTerminated());
        Assert.assertTrue(responseDto.getCanBeUnsuspended());
    }
}
