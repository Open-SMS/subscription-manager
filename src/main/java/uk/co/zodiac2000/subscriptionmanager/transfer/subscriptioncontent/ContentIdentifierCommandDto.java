package uk.co.zodiac2000.subscriptionmanager.transfer.subscriptioncontent;

import java.io.Serializable;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import static uk.co.zodiac2000.subscriptionmanager.ApplicationConstants.MAX_LENGTH_CONTENT_IDENTIFIER;

/**
 * Command DTO representing a content identifier.
 */
public class ContentIdentifierCommandDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotEmpty
    @Size(max = MAX_LENGTH_CONTENT_IDENTIFIER)
    private String contentIdentifier;

    /**
     * Zero-arg constructor to allow ObjectMapper to create this class.
     */
    public ContentIdentifierCommandDto() { }

    /**
     * Constructs a new ContentIdentifierCommandDto with the supplied contentIdentifier argument.
     * @param contentIdentifier the content identifier string
     */
    public ContentIdentifierCommandDto(final String contentIdentifier) {
        this.contentIdentifier = contentIdentifier;
    }

    /**
     * @return the content identifier string
     */
    public String getContentIdentifier() {
        return this.contentIdentifier;
    }
}
