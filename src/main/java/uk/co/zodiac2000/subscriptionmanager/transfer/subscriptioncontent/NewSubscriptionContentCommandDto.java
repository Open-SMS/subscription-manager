package uk.co.zodiac2000.subscriptionmanager.transfer.subscriptioncontent;

import java.io.Serializable;
import java.util.List;
import javax.validation.GroupSequence;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import static uk.co.zodiac2000.subscriptionmanager.ApplicationConstants.MAX_LENGTH_CONTENT_DESCRIPTION;
import static uk.co.zodiac2000.subscriptionmanager.ApplicationConstants.MAX_LONG_DIGITS;
import uk.co.zodiac2000.subscriptionmanager.constraint.Exists;
import uk.co.zodiac2000.subscriptionmanager.constraint.DataFormatChecks;
import uk.co.zodiac2000.subscriptionmanager.constraint.DataConsistencyChecks;

/**
 * Command DTO representing new subscription content.
 */
@GroupSequence({DataFormatChecks.class, DataConsistencyChecks.class, NewSubscriptionContentCommandDto.class})
public class NewSubscriptionContentCommandDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotEmpty(groups = DataFormatChecks.class)
    @Size(max = MAX_LENGTH_CONTENT_DESCRIPTION, groups = DataFormatChecks.class)
    private String contentDescription;

    @NotEmpty(groups = DataFormatChecks.class)
    @Valid
    private List<ContentIdentifierCommandDto> contentIdentifiers;

    @NotNull(groups = DataFormatChecks.class)
    @Digits(integer = MAX_LONG_DIGITS, fraction = 0, groups = DataFormatChecks.class)
    @Exists(expression = "@subscriptionResourceService.isPresent(#this)", groups = DataConsistencyChecks.class)
    private String subscriptionResourceId;
    /**
     * Zero-arg constructor to allow ObjectMapper to create this class.
     */
    public NewSubscriptionContentCommandDto() { }

    /**
     * Constructs a new NewSubscriptionContentCommandDto with the supplied arguments.
     * @param contentDescription description of the subscription content
     * @param contentIdentifiers the content identifiers associated with this subscription content
     * @param subscriptionResourceId the identifier of the subscription resource this subscription content is
     * associated with
     */
    public NewSubscriptionContentCommandDto(final String contentDescription,
            final List<ContentIdentifierCommandDto> contentIdentifiers, final String subscriptionResourceId) {
        this.contentDescription = contentDescription;
        this.contentIdentifiers = contentIdentifiers;
        this.subscriptionResourceId = subscriptionResourceId;
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

    /**
     * @return the identifier of the subscription resource this subscription content is associated with
     */
    public String getSubscriptionResourceId() {
        return this.subscriptionResourceId;
    }
}
