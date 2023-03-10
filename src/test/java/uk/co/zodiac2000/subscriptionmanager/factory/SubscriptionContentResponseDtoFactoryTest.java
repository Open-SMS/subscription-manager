package uk.co.zodiac2000.subscriptionmanager.factory;

import java.util.Optional;
import java.util.Set;
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
import uk.co.zodiac2000.subscriptionmanager.domain.subscriptioncontent.ContentIdentifier;
import uk.co.zodiac2000.subscriptionmanager.domain.subscriptioncontent.SubscriptionContent;
import uk.co.zodiac2000.subscriptionmanager.service.SubscriptionResourceService;
import uk.co.zodiac2000.subscriptionmanager.transfer.subscriptioncontent.SubscriptionContentResponseDto;
import uk.co.zodiac2000.subscriptionmanager.transfer.subscriptionresource.SubscriptionResourceResponseDto;

/**
 * Unit tests for SubscriptionContentResponseDtoFactory.
 */
@Listeners(MockitoTestNGListener.class)
public class SubscriptionContentResponseDtoFactoryTest {

    private static final Long ID = 42L;
    private static final String SUBSCRIPTION_CONTENT_DESCRIPTION = "Example Content";
    private static final Set<ContentIdentifier> CONTENT_IDENTIFIERS = Set.of(
            new ContentIdentifier("CONTENT-X"),
            new ContentIdentifier("CONTENT-A")
    );
    private static final Long SUBSCRIPTION_RESOURCE_ID = 87L;
    private static final String RESOURCE_URI = "https://example.com";
    private static final String RESOURCE_DESCRIPTION = "Example";

    private final SubscriptionResourceResponseDto subscriptionResourceResponseDto
            = new SubscriptionResourceResponseDto(SUBSCRIPTION_RESOURCE_ID, RESOURCE_URI, RESOURCE_DESCRIPTION);

    private SubscriptionContent subscriptionContent;

    @Mock
    private SubscriptionResourceService subscriptionResourceService;

    @InjectMocks
    private SubscriptionContentResponseDtoFactory factory;

    @BeforeMethod
    public void setUpSubscriptionResourceService() {
        Mockito.when(this.subscriptionResourceService.getSubscriptionResource(SUBSCRIPTION_RESOURCE_ID))
                .thenReturn(Optional.of(this.subscriptionResourceResponseDto));
    }

    @BeforeMethod
    public void setUpSubscriptionContent() {
        this.subscriptionContent = new SubscriptionContent(SUBSCRIPTION_CONTENT_DESCRIPTION, CONTENT_IDENTIFIERS,
                SUBSCRIPTION_RESOURCE_ID);
        ReflectionTestUtils.setField(this.subscriptionContent, "id", ID);
    }

    /**
     * Test subscriptionContentToResponseDto.
     */
    @Test
    public void testSubscriptionContentToResponseDto() {
        Optional<SubscriptionContentResponseDto> responseDto
                = this.factory.subscriptionContentToResponseDto(Optional.of(this.subscriptionContent));

        Assert.assertTrue(responseDto.isPresent());
        assertThat(responseDto.get(), allOf(
                hasProperty("id", is(ID)),
                hasProperty("contentDescription", is(SUBSCRIPTION_CONTENT_DESCRIPTION)),
                hasProperty("contentIdentifiers", contains(
                        is("CONTENT-A"),
                        is("CONTENT-X")
                )),
                hasProperty("subscriptionResource", allOf(
                        hasProperty("id", is(SUBSCRIPTION_RESOURCE_ID)),
                        hasProperty("resourceUri", is(RESOURCE_URI)),
                        hasProperty("resourceDescription", is(RESOURCE_DESCRIPTION))
                ))
        ));
    }
}
