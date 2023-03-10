package uk.co.zodiac2000.subscriptionmanager.converter;

import java.net.URI;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Unit tests for UriToStringConverter.
 */
public class UriToStringConverterTest {

    private static final URI URI_ATTRIBUTE = URI.create("https://example.com");
    private static final String URI_STRING = URI_ATTRIBUTE.toString();

    private final UriToStringConverter converter = new UriToStringConverter();

    /**
     * Test convertToDatabaseColumn.
     */
    @Test
    public void testConvertToDatabaseColumn() {
        Assert.assertEquals(this.converter.convertToDatabaseColumn(URI_ATTRIBUTE), URI_STRING);
    }

    /**
     * Test convertToDatabaseColumn when the argument is null.
     */
    @Test
    public void testConvertToDatabaseColumnNull() {
        Assert.assertNull(this.converter.convertToDatabaseColumn(null));
    }

    /**
     * Test convertToEntityAttribute.
     */
    @Test
    public void testConvertToEntityAttribute() {
        Assert.assertEquals(this.converter.convertToEntityAttribute(URI_STRING), URI_ATTRIBUTE);
    }

    /**
     * Test convertToEntityAttribute when the argument is an empty string.
     */
    @Test
    public void testConvertToEntityAttributeEmptyString() {
        Assert.assertNull(this.converter.convertToEntityAttribute(""));
    }

    /**
     * Test convertToEntityAttribute when the argument is null.
     */
    @Test
    public void testConvertToEntityAttributeNull() {
        Assert.assertNull(this.converter.convertToEntityAttribute(null));
    }
}
