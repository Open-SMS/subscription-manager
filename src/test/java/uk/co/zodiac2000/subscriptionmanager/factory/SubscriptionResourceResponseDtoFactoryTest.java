package uk.co.zodiac2000.subscriptionmanager.factory;

import java.net.URI;
import java.util.Optional;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import org.springframework.test.util.ReflectionTestUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import uk.co.zodiac2000.subscriptionmanager.domain.subscriptionresource.SubscriptionResource;
import uk.co.zodiac2000.subscriptionmanager.transfer.subscriptionresource.SubscriptionResourceResponseDto;

/**
 * Unit tests for SubscriptionResourceResponseDtoFactory.
 */
public class SubscriptionResourceResponseDtoFactoryTest {

    private static final long SUBSCRIPTION_RESOURCE_ID = 42L;
    private static final URI RESOURCE_URI = URI.create("https://example.com");
    private static final String RESOURCE_DESCRIPTION = "Example";

    private Optional<SubscriptionResource> subscriptionResource;

    private final SubscriptionResourceResponseDtoFactory factory = new SubscriptionResourceResponseDtoFactory();

    /**
     * Set up subscription resource test fixture.
     */
    @BeforeMethod
    public void setUpSubscriptionResource() {
        SubscriptionResource resource = new SubscriptionResource(RESOURCE_URI, RESOURCE_DESCRIPTION);
        ReflectionTestUtils.setField(resource, "id", SUBSCRIPTION_RESOURCE_ID);
        this.subscriptionResource = Optional.of(resource);
    }

    /**
     * Test subscriptionResourceToResponseDto.
     */
    @Test
    public void testSubscriptionResourceToResponseDto() {
        Optional<SubscriptionResourceResponseDto> responseDto
                = this.factory.subscriptionResourceToResponseDto(this.subscriptionResource);

        Assert.assertTrue(responseDto.isPresent());
        assertThat(responseDto.get(), allOf(
                hasProperty("id", is(SUBSCRIPTION_RESOURCE_ID)),
                hasProperty("resourceUri", is(RESOURCE_URI.toString())),
                hasProperty("resourceDescription", is(RESOURCE_DESCRIPTION))
        ));
    }

    /**
     * Test subscriptionResourceToResponseDto when the argument is an empty optional.
     */
    @Test
    public void testSubscriptionResourceToResponseDtoEmpty() {
        Optional<SubscriptionResourceResponseDto> responseDto
                = this.factory.subscriptionResourceToResponseDto(Optional.empty());

        Assert.assertTrue(responseDto.isEmpty());
    }
}
