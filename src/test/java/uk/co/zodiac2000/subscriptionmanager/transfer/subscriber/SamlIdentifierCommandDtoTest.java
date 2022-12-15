package uk.co.zodiac2000.subscriptionmanager.transfer.subscriber;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Unit tests for SamlIdentifierCommandDto.
 */
public class SamlIdentifierCommandDtoTest {

    private static final String ENTITY_ID = "urn:mace:ac.uk:sdss.ac.uk:provider:identity:dur.ac.uk";
    private static final String SCOPED_AFFILIATION = "staff@cardiff.ac.uk";

    /**
     * Test constructor and accessors.
     */
    @Test
    public void testAccessors() {
        SamlIdentifierCommandDto commandDto = new SamlIdentifierCommandDto(ENTITY_ID, SCOPED_AFFILIATION);

        Assert.assertEquals(commandDto.getEntityId(), ENTITY_ID);
        Assert.assertEquals(commandDto.getScopedAffiliation(), SCOPED_AFFILIATION);
    }
}
