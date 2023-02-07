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
import uk.co.zodiac2000.subscriptionmanager.transfer.contentidentifier.ContentIdentifierResponseDto;

/**
 * Integration tests for ContentIdentifierService.
 */
@SpringBootTest
@TestPropertySource(locations = "classpath:application-integration.properties")
public class ContentIdentifierServiceTestITCase extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    private ContentIdentifierService contentIdentifierService;

    @BeforeMethod
    public void loadTestData() {
        executeSqlScript("classpath:test_data/subscription_resource_test_data.sql", false);
        executeSqlScript("classpath:test_data/content_identifier_test_data.sql", false);
    }

    /**
     * Test getContentIdentifier.
     */
    @Test
    public void testGetContentIdentifier() {
        Optional<ContentIdentifierResponseDto> responseDto
                = this.contentIdentifierService.getContentIdentifier(100000004L);

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
     * Test getContentIdentifier when a content identifier is not found.
     */
    @Test
    public void testGetContentIdentifierNotFound() {
        Optional<ContentIdentifierResponseDto> responseDto
                = this.contentIdentifierService.getContentIdentifier(4321L);

        Assert.assertTrue(responseDto.isEmpty());
    }
}
