package uk.co.zodiac2000.subscriptionmanager.service;

import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import uk.co.zodiac2000.subscriptionmanager.repository.ClaimNameRepository;
import uk.co.zodiac2000.subscriptionmanager.transfer.subscriber.OidcIdentifierClaimRequestDto;
import uk.co.zodiac2000.subscriptionmanager.transfer.subscriber.OidcIdentifierClaimValueRequestDto;
import uk.co.zodiac2000.subscriptionmanager.transfer.subscriber.OidcIdentifierRequestDto;

/**
 * Integration tests for ClaimNameService.
 */
@SpringBootTest
@TestPropertySource(locations = "classpath:application-integration.properties")
public class ClaimNameServiceTestITCase extends AbstractTransactionalTestNGSpringContextTests {

    private static final String ISSUER = "https://accounts.google.com";
    private static final String NEW_CLAIM_NAME = "realmName";
    private static final String EXISTING_CLAIM_NAME = "groups";

    @Autowired
    private ClaimNameRepository repository;

    @Autowired
    private ClaimNameService service;

    @BeforeMethod
    public void loadTestData() {
        executeSqlScript("classpath:test_data/claim_name_test_data.sql", false);
    }

    /**
     * Test ensurePresent when the claim name does not exist in the system.
     */
    @Test
    public void testEnsurePresent() {
        this.service.ensurePresent(NEW_CLAIM_NAME);
        this.repository.flush();

        // Verify the claim name now exists
        Assert.assertTrue(this.repository.findByClaimName(NEW_CLAIM_NAME).isPresent());
    }

    /**
     * Test ensurePresent when the claim name exists in the system. There are no assertions for the behaviour of
     * this method other than it does not throw an exception.
     */
    @Test
    public void testEnsurePresentAlreadyExists() {
        this.service.ensurePresent(EXISTING_CLAIM_NAME);
    }

    /**
     * Test filterClaimsRequest.
     */
    @Test
    public void testFilterClaimsRequest() {
        OidcIdentifierRequestDto requestDto = new OidcIdentifierRequestDto(ISSUER, List.of(
                new OidcIdentifierClaimRequestDto(EXISTING_CLAIM_NAME, List.of(
                        new OidcIdentifierClaimValueRequestDto("e5389fa9123")
                )),
                new OidcIdentifierClaimRequestDto(NEW_CLAIM_NAME, List.of(
                        new OidcIdentifierClaimValueRequestDto("student")
                ))
        ));

        OidcIdentifierRequestDto filteredRequestDto = this.service.filterClaimsRequest(requestDto);

        assertThat(filteredRequestDto, allOf(
                hasProperty("issuer", is(ISSUER)),
                hasProperty("oidcIdentifierClaims", contains(
                        allOf(
                                hasProperty("claimName", is(EXISTING_CLAIM_NAME)),
                                hasProperty("claimValues", contains(
                                        hasProperty("claimValue", is("e5389fa9123"))
                                ))
                        )
                ))
        ));

    }
}
