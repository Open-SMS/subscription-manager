package uk.co.zodiac2000.subscriptionmanager.service;

import java.net.URI;
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
import uk.co.zodiac2000.subscriptionmanager.domain.subscriptionresource.SubscriptionResource;
import uk.co.zodiac2000.subscriptionmanager.repository.SubscriptionResourceRepository;
import uk.co.zodiac2000.subscriptionmanager.transfer.subscriptionresource.NewSubscriptionResourceCommandDto;
import uk.co.zodiac2000.subscriptionmanager.transfer.subscriptionresource.SubscriptionResourceCommandDto;
import uk.co.zodiac2000.subscriptionmanager.transfer.subscriptionresource.SubscriptionResourceResponseDto;

/**
 * Integration tests for SubscriptionResourceService.
 */
@SpringBootTest
@TestPropertySource(locations = "classpath:application-integration.properties")
public class SubscriptionResourceServiceTestITCase extends AbstractTransactionalTestNGSpringContextTests {

    private static final URI NEW_RESOURCE_URI = URI.create("https://zodiac2000.co.uk");
    private static final String NEW_RESOURCE_DESCRIPTION = "Zodiac 2000";

    @Autowired
    private SubscriptionResourceService service;

    @Autowired
    private SubscriptionResourceRepository subscriptionResourceRepository;

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

    /**
     * Test createSubscriptionResource.
     */
    @Test
    public void testCreateSubscriptionResource() {
        NewSubscriptionResourceCommandDto commandDto
                = new NewSubscriptionResourceCommandDto(NEW_RESOURCE_URI.toString(), NEW_RESOURCE_DESCRIPTION);
        Optional<SubscriptionResourceResponseDto> responseDto = this.service.createSubscriptionResource(commandDto);
        this.subscriptionResourceRepository.flush();

        Assert.assertTrue(responseDto.isPresent());
        Assert.assertEquals(responseDto.get().getResourceUri(), NEW_RESOURCE_URI.toString());
        Assert.assertEquals(responseDto.get().getResourceDescription(), NEW_RESOURCE_DESCRIPTION);

        Optional<SubscriptionResource> subscriptionResource
                = this.subscriptionResourceRepository.findById(responseDto.get().getId());
        Assert.assertTrue(subscriptionResource.isPresent());
        Assert.assertEquals(subscriptionResource.get().getResourceUri(), NEW_RESOURCE_URI);
        Assert.assertEquals(subscriptionResource.get().getResourceDescription(), NEW_RESOURCE_DESCRIPTION);
    }

    /**
     * Test getSubscriptionResourceByUri.
     */
    @Test
    public void testGetSubscriptionResourceByUri() {
        Optional<Long> id = this.service.getSubscriptionResourceIdByUri("urn:zodiac2000.co.uk:data");

        Assert.assertTrue(id.isPresent());
        Assert.assertEquals(id.get(), 100000004L);
    }

    /**
     * Test getSubscriptionResourceByUri when a subscription resource is not found.
     */
    @Test
    public void testGetSubscriptionResourceByUriNotFound() {
        Optional<Long> id = this.service.getSubscriptionResourceIdByUri("http://foo.com");

        Assert.assertTrue(id.isEmpty());
    }

    /**
     * Test updateSubscriptionResource.
     */
    @Test
    public void testUpdateSubscriptionResource() {
        SubscriptionResourceCommandDto commandDto
                = new SubscriptionResourceCommandDto(100000004L, "urn:zodiac2000.co.uk:info", "Information");
        Optional<SubscriptionResourceResponseDto> responseDto = this.service.updateSubscriptionResource(commandDto);
        this.subscriptionResourceRepository.flush();

        Assert.assertTrue(responseDto.isPresent());
        assertThat(responseDto.get(), allOf(
                hasProperty("id", is(100000004L)),
                hasProperty("resourceUri", is("urn:zodiac2000.co.uk:info")),
                hasProperty("resourceDescription", is("Information"))
        ));

        Optional<SubscriptionResource> subscriptionResource
                = this.subscriptionResourceRepository.findById(100000004L);
        Assert.assertTrue(subscriptionResource.isPresent());
        Assert.assertEquals(subscriptionResource.get().getResourceUri(), URI.create("urn:zodiac2000.co.uk:info"));
        Assert.assertEquals(subscriptionResource.get().getResourceDescription(), "Information");
    }

    /**
     * Test updateSubscriptionResource when the identified subscription resource does not exist.
     */
    @Test
    public void testUpdateSubscriptionResourceNotFound() {
        SubscriptionResourceCommandDto commandDto
                = new SubscriptionResourceCommandDto(99944L, "urn:zodiac2000.co.uk:info", "Information");
        Optional<SubscriptionResourceResponseDto> responseDto = this.service.updateSubscriptionResource(commandDto);
        this.subscriptionResourceRepository.flush();

        Assert.assertTrue(responseDto.isEmpty());
    }
}
