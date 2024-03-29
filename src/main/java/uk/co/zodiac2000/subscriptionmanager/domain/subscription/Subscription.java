package uk.co.zodiac2000.subscriptionmanager.domain.subscription;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Optional;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;
import uk.co.zodiac2000.subscriptionmanager.transfer.subscription.SubscriptionContentIdCommandDto;
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

    private boolean suspended;

    private boolean terminated;

    @NotNull
    private Long subscriptionContentId;

    @NotNull
    private Long subscriberId;

    /**
     * Zero-arg constructor for JPA.
     */
    public Subscription() { }

    /**
     * Constructs a new Subscription using the supplied arguments. Subscriptions created by this constructor are
     * neither terminated nor suspended.
     * @param optionalStartDate the date from which this subscription is active
     * @param optionalEndDate the date until which this subscription is active
     * @param subscriptionContentId the identifier of the subscription content that this subscription
     * provides access to
     * @param subscriberId the identifier of the subscriber that is the beneficiary of this subscription
     */
    public Subscription(final Optional<LocalDate> optionalStartDate, final Optional<LocalDate> optionalEndDate,
            final long subscriptionContentId, final long subscriberId) {
        if (!firstDateBeforeSecondDate(optionalStartDate, optionalEndDate)) {
            throw new IllegalArgumentException("startDate must be before or equal to endDate");
        }
        this.startDate = optionalStartDate.orElse(null);
        this.endDate = optionalEndDate.orElse(null);
        this.subscriptionContentId = subscriptionContentId;
        this.terminated = false;
        this.suspended = false;
        this.subscriberId = subscriberId;
    }

    /**
     * Terminates this subscription so that it is not active irrespective of the state of any other attributes. The
     * terminated state of a subscription cannot be reversed. If an attempt is made to terminate an already
     * terminated subscription then an IllegalStateException is thrown.
     * @throws IllegalStateException if an attempt is made to terminate an already terminated subscription
     */
    public void terminate() {
        if (!canBeTerminated()) {
            throw new IllegalStateException("This subscription cannot be terminated because it is already terminated");
        }
        this.terminated = true;
        this.suspended = true;
    }

    /**
     * Suspends this subscription so that it is not active irrespective of the state of any other attributes. The
     * suspended state can be reversed. If an attempt is made to suspend an already
     * suspended subscription then an IllegalStateException is thrown.
     * @throws IllegalStateException if an attempt is made to suspend an already suspended subscription
     */
    public void suspend() {
        if (!canBeSuspended()) {
            throw new IllegalStateException("This subscription cannot be suspended because it is already suspended");
        }
        this.suspended = true;
    }

    /**
     * Reverses suspension of this subscription (unsuspends). If the current date is within the subscription start
     * and end dates then the subscription becomes active. A subscription which is terminated cannot be unsuspended. A
     * subscription that is not suspended cannot be unsuspended.
     * @throws IllegalStateException if an attempt is made to unsuspend a terminated subscription, or unsuspend
     * a subscription that is not suspended
     */
    public void unsuspend() {
        if (this.terminated) {
            throw new IllegalStateException("This subscription cannot be unsuspended because it is terminated");
        }
        if (!this.suspended) {
            throw new IllegalStateException("This subscription cannot be unsuspended because it is not suspended");
        }
        this.suspended = false;
    }

    /**
     * @return true if this subscription can be terminated
     */
    public boolean canBeTerminated() {
        return !this.terminated;
    }

    /**
     * @return true if this subscription can be suspended
     */
    public boolean canBeSuspended() {
        return !this.suspended;
    }

    /**
     * @return true if this subscription can be unsuspended
     */
    public boolean canBeUnsuspended() {
        return !this.terminated && this.suspended;
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
     * Returns true is the subscription has been suspended. A terminated subscription is also a suspended subscription.
     * @return true if the subscription has been suspended
     */
    public boolean isSuspended() {
        return this.suspended;
    }

    /**
     * @return the identifier of the subscription content that this subscription provides access to
     */
    public Long getSubscriptionContentId() {
        return subscriptionContentId;
    }

    /**
     * @return the identifier of the subscriber that is the beneficiary of this subscription
     */
    public Long getSubscriberId() {
        return this.subscriberId;
    }

    /**
     * Returns true if this subscription is active. A subscription is active if atDate is between startDate and
     * endDate (inclusive), and the subscription is not terminated. If startDate or endDate is empty then the
     * subscription is active when atDate is before or equal to endDate, or after or equal to startDate respectively.
     * @param atDate the date at which the active state should be determined
     * @return true if the subscription is active
     */
    public boolean isActive(final LocalDate atDate) {
        return !this.suspended
                && this.getStartDate().map(d -> !atDate.isBefore(d)).orElse(true)
                && this.getEndDate().map(d -> !atDate.isAfter(d)).orElse(true);
    }

    /**
     * Sets the start and end dates of this subscription. The if start and end dates are present then the start date
     * must be before or equal to the end date.
     * @param commandDto command DTO representing the new start and end dates
     */
    public void setDates(final SubscriptionDatesCommandDto commandDto) {
        Optional<LocalDate> optionalStartDate = commandDto.getStartDate().map(LocalDate::parse);
        Optional<LocalDate> optionalEndDate = commandDto.getEndDate().map(LocalDate::parse);
        if (!firstDateBeforeSecondDate(optionalStartDate, optionalEndDate)) {
            throw new IllegalArgumentException("startDate must be before or equal to endDate");
        }
        this.startDate = optionalStartDate.orElse(null);
        this.endDate = optionalEndDate.orElse(null);
    }

    /**
     * @param firstDate the first date
     * @param secondDate the second date
     * @return true if firstDate or secondDate are empty, or if firstDate is before or equal to secondDate
     */
    private boolean firstDateBeforeSecondDate(final Optional<LocalDate> firstDate,
            final Optional<LocalDate> secondDate) {
        return firstDate.map(s ->
                secondDate.map(e -> e.isAfter(s) || e.equals(s)).orElse(true)
        ).orElse(true);
    }

    /**
     * Sets the identifier of the subscription content associated with this subscription.
     * @param commandDto command DTO representing the new subscription content identifier
     */
    public void setSubscriptionContentId(final SubscriptionContentIdCommandDto commandDto) {
        this.subscriptionContentId = Long.valueOf(commandDto.getSubscriptionContentId());
    }
}
