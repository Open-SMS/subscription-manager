package uk.co.zodiac2000.subscriptionmanager.factory;

import java.util.Optional;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.testng.MockitoTestNGListener;
import org.springframework.test.util.ReflectionTestUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import uk.co.zodiac2000.subscriptionmanager.domain.contentidentifier.ContentIdentifier;
import uk.co.zodiac2000.subscriptionmanager.service.SubscriptionResourceService;
import uk.co.zodiac2000.subscriptionmanager.transfer.contentidentifier.ContentIdentifierResponseDto;
import uk.co.zodiac2000.subscriptionmanager.transfer.resource.SubscriptionResourceResponseDto;

/**
 * Unit tests for ContentIdentifierResponseDtoFactory.
 */
@Listeners(MockitoTestNGListener.class)
public class ContentIdentifierResponseDtoFactoryTest {

    private static final Long ID = 42L;
    private static final Long SUBSCRIPTION_RESOURCE_ID = 87L;
    private static final String RESOURCE_URI = "https://example.com";
    private static final String RESOURCE_DESCRIPTION = "Example";

    private final SubscriptionResourceResponseDto subscriptionResourceResponseDto
            = new SubscriptionResourceResponseDto(SUBSCRIPTION_RESOURCE_ID, RESOURCE_URI, RESOURCE_DESCRIPTION);

    private ContentIdentifier contentIdentifier;

    @Mock
    private SubscriptionResourceService subscriptionResourceService;

    @InjectMocks
    private ContentIdentifierResponseDtoFactory factory;

    @BeforeMethod
    public void setUpSubscriptionResourceService() {
        Mockito.when(this.subscriptionResourceService.getSubscriptionResource(SUBSCRIPTION_RESOURCE_ID))
                .thenReturn(Optional.of(this.subscriptionResourceResponseDto));
    }

    @BeforeMethod
    public void setUpContentIdentifier() {
        this.contentIdentifier = new ContentIdentifier(SUBSCRIPTION_RESOURCE_ID);
        ReflectionTestUtils.setField(this.contentIdentifier, "id", ID);
    }

    /**
     * Test contentIdentifierToResponseDto.
     */
    @Test
    public void testContentIdentifierToResponseDto() {
        Optional<ContentIdentifierResponseDto> responseDto
                = this.factory.contentIdentifierToResponseDto(Optional.of(this.contentIdentifier));

        Assert.assertTrue(responseDto.isPresent());
        assertThat(responseDto.get(), allOf(
                hasProperty("id", is(ID)),
                hasProperty("subscriptionResource", allOf(
                        hasProperty("id", is(SUBSCRIPTION_RESOURCE_ID)),
                        hasProperty("resourceUri", is(RESOURCE_URI)),
                        hasProperty("resourceDescription", is(RESOURCE_DESCRIPTION))
                ))
        ));
    }
}
