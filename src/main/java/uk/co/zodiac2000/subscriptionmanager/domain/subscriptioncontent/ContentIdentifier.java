package uk.co.zodiac2000.subscriptionmanager.domain.subscriptioncontent;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Objects;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import static uk.co.zodiac2000.subscriptionmanager.ApplicationConstants.MAX_LENGTH_CONTENT_IDENTIFIER;

/**
 * Represents a content identifier associated with a SubscriptionContent object.
 */
@Embeddable
public class ContentIdentifier implements Serializable, Comparable<ContentIdentifier> {

    private static final long serialVersionUID = 394839L;

    @NotEmpty
    @Size(max = MAX_LENGTH_CONTENT_IDENTIFIER)
    private String contentIdentifier;

    /**
     * Zero-args constructor for JPA.
     */
    public ContentIdentifier() { }

    /**
     * Constructs a new ContentIdentifier with the supplied arguments.
     * @param contentIdentifier the content identifier string
     */
    public ContentIdentifier(final String contentIdentifier) {
        this.contentIdentifier = Objects.requireNonNull(contentIdentifier);
    }

    /**
     * @return the content identifier string
     */
    public String getContentIdentifier() {
        return this.contentIdentifier;
    }

    /**
     * Indicates whether {@code other} is equal to this object. Non-null objects of the same class and with
     * equal {@code contentIdentifier} fields are considered equal.
     * @param other the reference object with which to compare
     * @return true if this object is equal to the {@code other} argument; false otherwise.
     */
    @Override
    public boolean equals(final Object other) {
        return other != null
                && this.getClass() == other.getClass()
                && Objects.equals(this.getContentIdentifier(), ((ContentIdentifier) other).getContentIdentifier());
    }

    /**
     * Returns a hash code value for the object.
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.getContentIdentifier());
    }

    /**
     * Compares this object with the specified object for order. Returns a negative integer, zero, or a positive
     * integer as this object is less than, equal to, or greater than the specified object. Comparison is based
     * on comparison of the {@code contentIdentifier} field.
     * @param other the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than
     * the specified object.
     */
    @Override
    public int compareTo(final ContentIdentifier other) {
        return Comparator.comparing(ContentIdentifier::getContentIdentifier)
                .compare(this, other);
    }
}
