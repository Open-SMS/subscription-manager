package uk.co.zodiac2000.subscriptionmanager.constraint;

/**
 * Interface used by bean validation {@code @GroupSequence} representing a group of constraints that check the format
 * of data, for example whether a field contains an integer. In certain circumstances these checks should be performed
 * before checks involving the consistency of the data because the consistency checks may make assumptions about the
 * format of the data. For example, when verifying that an identifier representing an associated object references
 * an object that exists in the system it is useful to verify the identifier contains integer data before verifying
 * the referenced object exists.
 */
public interface DataFormatChecks {
}
