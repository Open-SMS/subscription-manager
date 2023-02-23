package uk.co.zodiac2000.subscriptionmanager.service;

import java.util.List;
import java.util.Optional;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import uk.co.zodiac2000.subscriptionmanager.domain.subscriptioncontent.SubscriptionContent;
import uk.co.zodiac2000.subscriptionmanager.repository.SubscriptionContentRepository;
import uk.co.zodiac2000.subscriptionmanager.transfer.subscriptioncontent.ContentIdentifierCommandDto;
import uk.co.zodiac2000.subscriptionmanager.transfer.subscriptioncontent.NewSubscriptionContentCommandDto;
import uk.co.zodiac2000.subscriptionmanager.transfer.subscriptioncontent.SubscriptionContentResponseDto;

/**
 * Integration tests for SubscriptionContentService.
 */
@SpringBootTest
@TestPropertySource(locations = "classpath:application-integration.properties")
public class SubscriptionContentServiceTestITCase extends AbstractTransactionalTestNGSpringContextTests {

    private static final String NEW_CONTENT_DESCRIPTION = "Index Islamicus";
    private static final List<ContentIdentifierCommandDto> NEW_CONTENT_IDENTIFIERS = List.of(
            new ContentIdentifierCommandDto("Biblography"),
            new ContentIdentifierCommandDto("GeneralIndex")
    );
    private static final String NEW_SUBSCRIPTION_RESOURCE_ID = "100000003";
    private static final NewSubscriptionContentCommandDto NEW_SUBSCRIPTION_CONTENT_COMMAND_DTO
            = new NewSubscriptionContentCommandDto(NEW_CONTENT_DESCRIPTION, NEW_CONTENT_IDENTIFIERS,
                    NEW_SUBSCRIPTION_RESOURCE_ID);

    @Autowired
    private SubscriptionContentService subscriptionContentService;

    @Autowired
    private SubscriptionContentRepository subscriptionContentRepository;

    @BeforeMethod
    public void loadTestData() {
        executeSqlScript("classpath:test_data/subscription_resource_test_data.sql", false);
        executeSqlScript("classpath:test_data/subscription_content_test_data.sql", false);
    }

    /**
     * Test getSubscriptionContent.
     */
    @Test
    public void testGetSubscriptionContent() {
        Optional<SubscriptionContentResponseDto> responseDto
                = this.subscriptionContentService.getSubscriptionContent(100000004L);

        Assert.assertTrue(responseDto.isPresent());
        assertThat(responseDto.get(), allOf(
                hasProperty("id", is(100000004L)),
                hasProperty("contentDescription", is("Zodiac 2000 data: video")),
                hasProperty("contentIdentifiers", contains(is("content-video"))),
                hasProperty("subscriptionResource", allOf(
                        hasProperty("id", is(100000004L)),
                        hasProperty("resourceUri", is("urn:zodiac2000.co.uk:data")),
                        hasProperty("resourceDescription", is("Zodiac 2000 Data"))
                ))
        ));
    }

    /**
     * Test getSubscriptionContent when not found.
     */
    @Test
    public void testGetSubscriptionContentNotFound() {
        Optional<SubscriptionContentResponseDto> responseDto
                = this.subscriptionContentService.getSubscriptionContent(4321L);

        Assert.assertTrue(responseDto.isEmpty());
    }

    /**
     * Test isPresent.
     */
    @Test
    public void testIsPresent() {
        Assert.assertTrue(this.subscriptionContentService.isPresent(100000004L));
    }

    /**
     * Test createSubscriptionContent.
     */
    @Test
    public void testCreateSubscriptionContent() {
        Optional<SubscriptionContentResponseDto> responseDto
                = this.subscriptionContentService.createSubscriptionContent(NEW_SUBSCRIPTION_CONTENT_COMMAND_DTO);
        this.subscriptionContentRepository.flush();

        Assert.assertTrue(responseDto.isPresent());
        Assert.assertNotNull(responseDto.get().getId());
        assertThat(responseDto.get(), allOf(
                hasProperty("contentDescription", is(NEW_CONTENT_DESCRIPTION)),
                hasProperty("contentIdentifiers", containsInAnyOrder(
                        is("Biblography"), is("GeneralIndex")
                )),
                hasProperty("subscriptionResource", hasProperty("id", is(Long.valueOf(NEW_SUBSCRIPTION_RESOURCE_ID))))
        ));

        Optional<SubscriptionContent> subscriptionContent
                = this.subscriptionContentRepository.findById(responseDto.get().getId());
        Assert.assertTrue(subscriptionContent.isPresent());
        assertThat(subscriptionContent.get(), allOf(
                hasProperty("contentDescription", is(NEW_CONTENT_DESCRIPTION)),
                hasProperty("contentIdentifiers", containsInAnyOrder(
                        hasProperty("contentIdentifier", is("Biblography")),
                        hasProperty("contentIdentifier", is("GeneralIndex"))
                )),
                hasProperty("subscriptionResourceId", is(Long.valueOf(NEW_SUBSCRIPTION_RESOURCE_ID)))
        ));
    }
}
