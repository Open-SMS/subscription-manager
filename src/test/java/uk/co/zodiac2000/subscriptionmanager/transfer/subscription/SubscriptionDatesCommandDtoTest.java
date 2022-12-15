package uk.co.zodiac2000.subscriptionmanager.transfer.subscription;

import java.util.Optional;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Unit tests for SubscriptionDatesCommandDto.
 */
public class SubscriptionDatesCommandDtoTest {

    private static final Optional<String> START_DATE = Optional.of("2021-02-03");
    private static final Optional<String> END_DATE = Optional.of("2022-02-03");

    /**
     * Test constructor and accessors.
     */
    @Test
    public void testAccessors() {
        SubscriptionDatesCommandDto commandDto = new SubscriptionDatesCommandDto(START_DATE, END_DATE);

        Assert.assertEquals(commandDto.getStartDate(), START_DATE);
        Assert.assertEquals(commandDto.getEndDate(), END_DATE);
    }

    /**
     * Test zero-arg constructor.
     */
    @Test
    public void testZeroArgConstructor() {
        SubscriptionDatesCommandDto commandDto = new SubscriptionDatesCommandDto();

        Assert.assertNotNull(commandDto);
    }
}
