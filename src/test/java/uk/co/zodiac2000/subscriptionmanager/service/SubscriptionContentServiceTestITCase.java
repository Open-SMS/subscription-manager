package uk.co.zodiac2000.subscriptionmanager.service;

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
import uk.co.zodiac2000.subscriptionmanager.transfer.subscriptioncontent.SubscriptionContentResponseDto;

/**
 * Integration tests for SubscriptionContentService.
 */
@SpringBootTest
@TestPropertySource(locations = "classpath:application-integration.properties")
public class SubscriptionContentServiceTestITCase extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    private SubscriptionContentService subscriptionContentService;

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
}
