package uk.co.zodiac2000.subscriptionmanager.transfer.subscriber;

import java.util.List;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Unit tests for OidcIdentifiersCommandDto.
 */
public class OidcIdentifiersCommandDtoTest {

    private static final List<OidcIdentifierCommandDto> IDENTIFIERS = List.of();

    /**
     * Test constructor and accessor.
     */
    @Test
    public void testAccessors() {
        OidcIdentifiersCommandDto commandDto = new OidcIdentifiersCommandDto(IDENTIFIERS);

        Assert.assertEquals(commandDto.getOidcIdentifiers(), IDENTIFIERS);
    }

    /**
     * Test zero-arg constructor.
     */
    @Test
    public void testZeroArgConstructor() {
        OidcIdentifiersCommandDto commandDto = new OidcIdentifiersCommandDto();

        Assert.assertNotNull(commandDto);
    }
}
