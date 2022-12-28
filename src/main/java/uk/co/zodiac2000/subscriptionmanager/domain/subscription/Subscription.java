package uk.co.zodiac2000.subscriptionmanager.domain.subscription;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Optional;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import uk.co.zodiac2000.subscriptionmanager.transfer.subscription.SubscriptionContentIdentifierCommandDto;
import uk.co.zodiac2000.subscriptionmanager.transfer.subscription.SubscriptionDatesCommandDto;

/**
 * Represents a subscription that defines the rights for an identified subscriber to access content.
 */
@Entity
public class Subscription implements Serializable {

    private static final long serialVersionUID = 12491L;

    @Id
    @SequenceGenerator(name = "subscription_id_gen", sequenceName = "subscription_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "subscription_id_gen")
    private Long id;

    private LocalDate startDate;

    private LocalDate endDate;

    private boolean terminated;

    @NotEmpty
    private String contentIdentifier;

    @NotNull
    private Long subscriberId;

    /**
     * Zero-arg constructor for JPA.
     */
    public Subscription() { }

    /**
     * Constructs a new Subscription using the supplied arguments. Subscriptions created by this constructor are not
     * terminated.
     * @param optionalStartDate the date from which this subscription is active
     * @param optionalEndDate the date until which this subscription is active
     * @param contentIdentifier a string that describes the content that is the subject of this subscription
     * @param subscriberId the identifier of the subscriber that is the beneficiary of this subscription
     */
    public Subscription(Optional<LocalDate> optionalStartDate, Optional<LocalDate> optionalEndDate,
            String contentIdentifier, Long subscriberId) {
        if (!startDateBeforeEndDate(optionalStartDate, optionalEndDate)) {
            throw new IllegalArgumentException("startDate must be before or equal to endDate");
        }
        this.startDate = optionalStartDate.orElse(null);
        this.endDate = optionalEndDate.orElse(null);
        this.contentIdentifier = contentIdentifier;
        this.terminated = false;
        this.subscriberId = subscriberId;
    }

    /**
     * Terminates this subscription so that it is not active irrespective of the state of any other attributes.
     */
    public void terminate() {
        this.terminated = true;
    }

    /**
     * @return the subscription identifier
     */
    public Long getId() {
        return this.id;
    }

    /**
     * @return the date from which this subscription is active
     */
    public Optional<LocalDate> getStartDate() {
        return Optional.ofNullable(this.startDate);
    }

    /**
     * @return the date until which this subscription is active
     */
    public Optional<LocalDate> getEndDate() {
        return Optional.ofNullable(this.endDate);
    }

    /**
     * @return if true this subscription has been terminated
     */
    public boolean isTerminated() {
        return this.terminated;
    }

    /**
     * @return a string that describes the content that is the subject of this subscription
     */
    public String getContentIdentifier() {
        return contentIdentifier;
    }

    /**
     * @return the identifier of the subscriber that is the beneficiary of this subscription
     */
    public Long getSubscriberId() {
        return this.subscriberId;
    }

    /**
     * Returns true if this subscription is active. A subscription is active if atDate is between startDate and
     * endDate (inclusive), and the subscription is not terminated. If startDate or endDate is empty then the subscription
     * is active when atDate is before or equal to endDate, or after or equal to startDate respectively.
     * @param atDate the date at which the active state should be determined
     * @return true if the subscription is active
     */
    public boolean isActive(LocalDate atDate) {
        return !this.terminated
                && this.getStartDate().map(d -> !atDate.isBefore(d)).orElse(true)
                && this.getEndDate().map(d -> !atDate.isAfter(d)).orElse(true);
    }

    /**
     * Sets the start and end dates of this subscription. The if start and end dates are present then the start date
     * must be before or equal to the end date.
     * @param commandDto command DTO representing the new start and end dates
     */
    public void setDates(SubscriptionDatesCommandDto commandDto) {
        Optional<LocalDate> optionalStartDate = commandDto.getStartDate().map(LocalDate::parse);
        Optional<LocalDate> optionalEndDate = commandDto.getEndDate().map(LocalDate::parse);
        if (!startDateBeforeEndDate(optionalStartDate, optionalEndDate)) {
            throw new IllegalArgumentException("startDate must be before or equal to endDate");
        }
        this.startDate = optionalStartDate.orElse(null);
        this.endDate = optionalEndDate.orElse(null);
    }

    /**
     * @param startDate
     * @param endDate
     * @return true if startDate or endDate are empty, or if startDate is before or equal to endDate
     */
    private boolean startDateBeforeEndDate(Optional<LocalDate> startDate, Optional<LocalDate> endDate) {
        return startDate.map(s ->
                endDate.map(e -> e.isAfter(s) || e.equals(s)).orElse(true)
        ).orElse(true);
    }

    /**
     * Sets the content identifier associated with this subscription.
     * @param commandDto command DTO representing the new content identifier
     */
    public void setContentIdentifier(SubscriptionContentIdentifierCommandDto commandDto) {
        this.contentIdentifier = commandDto.getContentIdentifier();
    }
}