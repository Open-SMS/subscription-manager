package uk.co.zodiac2000.subscriptionmanager.transfer.subscriptioncontent;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Unit tests for ContentIdentifierCommandDto.
 */
public class ContentIdentifierCommandDtoTest {

    private static final String CONTENT_IDENTIFIER = "CONTENT-ONE";

    /**
     * Test constructor and accessor.
     */
    @Test
    public void testConstructor() {
        ContentIdentifierCommandDto commandDto = new ContentIdentifierCommandDto(CONTENT_IDENTIFIER);

        Assert.assertEquals(commandDto.getContentIdentifier(), CONTENT_IDENTIFIER);
    }

    /**
     * Test zero-arg constructor.
     */
    @Test
    public void testZeroArgConstructor() {
        ContentIdentifierCommandDto commandDto = new ContentIdentifierCommandDto();

        Assert.assertNotNull(commandDto);
    }
}
