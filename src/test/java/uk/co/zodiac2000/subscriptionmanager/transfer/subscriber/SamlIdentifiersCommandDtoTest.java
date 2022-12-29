package uk.co.zodiac2000.subscriptionmanager.transfer.subscriber;

import java.util.List;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Unit tests for SamlIdentifiersCommandDto.
 */
public class SamlIdentifiersCommandDtoTest {

    private static final List<SamlIdentifierCommandDto> IDENTIFIERS = List.of();

    /**
     * Test accessors.
     */
    @Test
    public void testAccessors() {
        SamlIdentifiersCommandDto commandDto = new SamlIdentifiersCommandDto(IDENTIFIERS);

        Assert.assertEquals(commandDto.getSamlIdentifiers(), IDENTIFIERS);
    }

    /**
     * Test zero-arg constructor.
     */
    @Test
    public void testZeroArgConstructor() {
        SamlIdentifiersCommandDto commandDto = new SamlIdentifiersCommandDto();

        Assert.assertNotNull(commandDto);
    }
}
