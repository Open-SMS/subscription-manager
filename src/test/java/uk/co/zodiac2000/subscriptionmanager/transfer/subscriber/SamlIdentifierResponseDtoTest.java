package uk.co.zodiac2000.subscriptionmanager.transfer.subscriber;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Unit tests for SamlIdentifierResponseDto.
 */
public class SamlIdentifierResponseDtoTest {

    private static final String ENTITY_ID = "urn:mace:ac.uk:sdss.ac.uk:provider:identity:dur.ac.uk";
    private static final String SCOPED_AFFILIATION = "staff@cardiff.ac.uk";

    /**
     * Test constructor and accessors.
     */
    @Test
    public void testAccessors() {
        SamlIdentifierResponseDto responseDto = new SamlIdentifierResponseDto(ENTITY_ID, SCOPED_AFFILIATION);

        Assert.assertEquals(responseDto.getEntityId(), ENTITY_ID);
        Assert.assertEquals(responseDto.getScopedAffiliation(), SCOPED_AFFILIATION);
    }
}
