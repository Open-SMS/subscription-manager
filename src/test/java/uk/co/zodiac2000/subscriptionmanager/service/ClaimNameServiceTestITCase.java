package uk.co.zodiac2000.subscriptionmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import uk.co.zodiac2000.subscriptionmanager.repository.ClaimNameRepository;

/**
 * Integration tests for ClaimNameService.
 */
@SpringBootTest
@TestPropertySource(locations = "classpath:application-integration.properties")
public class ClaimNameServiceTestITCase extends AbstractTransactionalTestNGSpringContextTests {

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
}
