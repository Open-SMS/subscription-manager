package uk.co.zodiac2000.subscriptionmanager.transfer.subscriptioncontent;

import java.io.Serializable;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import static uk.co.zodiac2000.subscriptionmanager.ApplicationConstants.MAX_LENGTH_CONTENT_DESCRIPTION;

/**
 * Command DTO representing new subscription content.
 */
public class NewSubscriptionContentCommandDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotEmpty
    @Size(max = MAX_LENGTH_CONTENT_DESCRIPTION)
    private String contentDescription;

    @NotEmpty
    @Valid
    private List<ContentIdentifierCommandDto> contentIdentifiers;

    /**
     * Zero-arg constructor to allow ObjectMapper to create this class.
     */
    public NewSubscriptionContentCommandDto() { }

    /**
     * Constructs a new NewSubscriptionContentCommandDto with the supplied arguments.
     * @param contentDescription description of the subscription content
     * @param contentIdentifiers the content identifiers associated with this subscription content
     */
    public NewSubscriptionContentCommandDto(final String contentDescription,
            final List<ContentIdentifierCommandDto> contentIdentifiers) {
        this.contentDescription = contentDescription;
        this.contentIdentifiers = contentIdentifiers;
    }

    /**
     * @return description of the subscription content
     */
    public String getContentDescription() {
        return contentDescription;
    }

    /**
     * @return the content identifiers associated with this subscription content
     */
    public List<ContentIdentifierCommandDto> getContentIdentifiers() {
        return contentIdentifiers;
    }
}
