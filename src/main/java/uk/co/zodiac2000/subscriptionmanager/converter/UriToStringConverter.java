package uk.co.zodiac2000.subscriptionmanager.converter;

import java.net.URI;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * AttributeConverter implementation that can convert between URI and String objects. This is used to allow JPA
 * entities to contain URI fields that are converted to VARCHARs when persisted to the database, and the reverse
 * operation when retrieved.
 */
@Converter(autoApply = true)
public class UriToStringConverter implements AttributeConverter<URI, String> {

    /**
     * Converts a URI to a String when entities with a URI field are persisted. If {@code uri} is null then null
     * is returned.
     * @param uri URI to convert
     * @return a string representation of the URI.
     */
    @Override
    public String convertToDatabaseColumn(final URI uri) {
        if (uri == null) {
            return null;
        }
        return uri.toString();
    }

    /**
     * Converts a String into a URI object which when entities containing a URI field are retrieved. If
     * {@code uriString} is null or an empty string then null is returned.
     * @param uriString string representation of the URI
     * @return a URI
     */
    @Override
    public URI convertToEntityAttribute(final String uriString) {
        if (uriString == null || uriString.isEmpty()) {
            return null;
        }
        return URI.create(uriString);
    }
}
