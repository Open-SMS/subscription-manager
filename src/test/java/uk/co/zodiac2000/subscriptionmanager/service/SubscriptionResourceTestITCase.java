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
import uk.co.zodiac2000.subscriptionmanager.transfer.resource.SubscriptionResourceResponseDto;

/**
 * Integration tests for SubscriptionResourceService.
 */
@SpringBootTest
@TestPropertySource(locations = "classpath:application-integration.properties")
public class SubscriptionResourceTestITCase extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    private SubscriptionResourceService service;

    @BeforeMethod
    public void loadTestData() {
        executeSqlScript("classpath:test_data/subscription_resource_test_data.sql", false);
    }

    /**
     * Test getSubscriptionResource.
     */
    @Test
    public void testGetSubscriptionResource() {
        Optional<SubscriptionResourceResponseDto> responseDto = this.service.getSubscriptionResource(100000004L);

        Assert.assertTrue(responseDto.isPresent());
        assertThat(responseDto.get(), allOf(
                hasProperty("id", is(100000004L)),
                hasProperty("resourceUri", is("urn:zodiac2000.co.uk:data")),
                hasProperty("resourceDescription", is("Zodiac 2000 Data"))
        ));
    }

    /**
     * Test getSubscriptionResource when a subscription resource is not found.
     */
    @Test
    public void testGetSubscriptionResourceNotFound() {
        Optional<SubscriptionResourceResponseDto> responseDto = this.service.getSubscriptionResource(999L);

        Assert.assertTrue(responseDto.isEmpty());
    }
}
